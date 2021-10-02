// @ts-nocheck
import React, { useEffect, useState } from "react"
import { EntitiesURLs, EntitiesCRUD_API } from "../api/EntitiesCRUD_API"
import { Table, Input, Popconfirm, Form, Typography, Layout, Space, Button, message, Popover, Tag } from "antd"
import { Content, Header } from "antd/lib/layout/layout"
import { CloseOutlined, DeleteOutlined, PlusOutlined, SearchOutlined, DeleteColumnOutlined, LeftOutlined, MehOutlined } from "@ant-design/icons"
import { AntdColumn, buildColumnsByObject, buildCreationForm } from "./PresentersGenerator"
import Highlighter from 'react-highlight-words'
import EditableCell from "./EditableCell"
import { DRAGON_TYPE, DRAGONS_API } from "../api/Dragons"
import { PERSONS_API } from "../api/Persons";

const EntityTable: React.FC<{ entity: EntitiesURLs, template: object }> =
    ({ entity, template }) => {

    const columns = buildColumnsByObject(template)
    const [form] = Form.useForm()
    const [items, setItems] = useState<Array<object>>([])
    const [isLoading, setIsLoaded] = useState<boolean>(true)
    const [editingKey, setEditingKey] = useState(0)
    const [selectedRowKeys, setSelectedRowKeys] = useState<Array<number>>([])
    const [isFormVisible, setFormVisibility] = useState<boolean>(false)
    const [searchText, setSearchText] = useState<string>('')
    const [searchedColumn, setSearchedColumn] = useState<string>('')

    const [dragonsTypeStat, setDragonsTypeStat] = useState<string>('')

    const getColumnSearchProps = dataIndex => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
            <div>
                <Input placeholder={ `Search ${dataIndex}` }
                       value={ selectedKeys[0] }
                       onChange={ e => setSelectedKeys(e.target.value ? [e.target.value] : []) }
                       onPressEnter={ () => handleSearch(selectedKeys, confirm, dataIndex) }
                />
                <Space>
                    <Button type="primary"
                            icon={ <SearchOutlined/> }
                            onClick={() => handleSearch(selectedKeys, confirm, dataIndex)}
                    >
                        Search
                    </Button>
                    <Button onClick={() => handleReset(clearFilters)}>
                        Reset
                    </Button>
                </Space>
            </div>
        ),
        filterIcon: filtered => <SearchOutlined style={{ color: filtered ? '#1890ff' : undefined }}/>,
        onFilter: (value, record) => record[dataIndex] ? record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()) : '',
        render: (text: string) =>
            searchedColumn === dataIndex ?
                <Highlighter
                    highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                    searchWords={ [searchText] }
                    autoEscape
                    textToHighlight={ text?.toString() ?? '' }
                />
             : text
    })

    const handleSearch = (selectedKeys: Array<string>, confirm: Function, dataIndex: string) => {
        confirm()
        setSearchText(selectedKeys[0])
        setSearchedColumn(dataIndex)
    }

    const handleReset = clearFilters => {
        clearFilters()
        setSearchText('')
    }

    const isEditing = (record): boolean => record.key === editingKey

    const cancel = () => setEditingKey(0)

    const edit = (record) => {
        form.setFieldsValue(record)
        setEditingKey(record.key)
    }

    const addRecord = data => {
        EntitiesCRUD_API.add(entity, data)
            .then( answer => setItems([...items, {...data, id: answer.id}]))
            .catch( err => message.error(err) )
    }

    const save = async key => {
        setEditingKey(0)
        /*try {
            const row = await form.validateFields()
            const newData = [...columns]
            // @ts-ignore
            const index = newData.findIndex((item) => key === item.key)
            if (index > -1) newData.splice(index, 1, { ...newData[index], ...row })
            else newData.push(row)
            setItems(newData)
            setEditingKey(0)
        } catch (errInfo) {
            // TODO: красочный вывод
            console.log("Validate Failed:", errInfo);
        }*/
    }

    useEffect(() => {
        EntitiesCRUD_API.getAll(entity)
            .then( data => {
                setIsLoaded(false)
                setItems(data.map( it => ({ ...it, key: it.id }) ))
            }).catch( err => message.error(err) )
    }, [entity])

    const mergedColumns = [...columns.map( (col: AntdColumn) => ({ ...col, ...getColumnSearchProps(col.dataIndex) })), {
        title: "ACTIONS",
        dataIndex: "actions",
        render: ( _, record ) =>
            isEditing(record) ?
                <span>
                    <a onClick={ () => save(record.key) }
                       style={{ marginRight: 8}}
                    >
                        Save
                    </a>
                    <Popconfirm title="Sure to cancel?" onConfirm={ cancel }>
                        <a>
                            Cancel
                        </a>
                    </Popconfirm>
                </span>
                :
                <Typography.Link disabled={editingKey !== 0}
                                 onClick={() => {
                                     form.setFieldsValue(record)
                                     setEditingKey(record.key)
                                 }}>
                    Edit
                </Typography.Link>
    }].map( (col: AntdColumn) => {
        return !col.editable ? col : {
            ...col,
            onCell: record => ({
                record,
                inputType: col.dataIndex === "age" ? "number" : "text",
                dataIndex: col.dataIndex,
                title: col.title,
                editing: isEditing(record)
            })
        }
    })

    const actions = [
        <Popover
            content={
                <div>
                    { buildCreationForm(template, addRecord) }
                    <Button shape="round"
                            icon={<CloseOutlined/>}
                            size="small"
                            onClick={ () => setFormVisibility(false) }
                    />
                </div>
            }
            visible={ isFormVisible }>
            <Button type="primary"
                    icon={ <PlusOutlined/> }
                    ghost={ true }
                    onClick={ () => setFormVisibility(true) }
            >
                Add record
            </Button>
        </Popover>,
        <Button icon={ <DeleteOutlined/> }
                ghost={ true }
                danger
                onClick={ () => {
                    if (selectedRowKeys.length === 0) message.warning('Nothing is selected')
                    else Promise.all(selectedRowKeys.map( it =>
                        EntitiesCRUD_API.delete(entity, it) )).then( () => {
                        setItems(items.filter( it => !selectedRowKeys.includes(it['id']) ))
                        message.success('All entities was deleted')
                    })
                }}>
            Remove
        </Button>
    ]
    // да, так делать нельзя, но сроки...
    switch (entity.split('/').slice(-2)[0]) {
        case 'dragons':
            actions.push(
                <Popover trigger="hover"
                         onVisibleChange={ visible => {
                             if (visible) DRAGONS_API.getGroupedByType().then( stat => setDragonsTypeStat(stat) )
                         } }
                         content={
                             Object.keys(dragonsTypeStat).map( it =>
                                 <Tag color={ DRAGON_TYPE[it] }>{it} = {dragonsTypeStat[it]}</Tag>
                             )
                         }
                         title="Count dragons by type"
                >
                    <Button icon={ <DeleteColumnOutlined/> }
                            ghost={ true }
                    >
                        Statistic
                    </Button>
                </Popover>
            )
            actions.push(
                // find_with_killer_weaker_then
                <Popover trigger="click"
                         content={
                             <Input.Search onSearch={
                                 id => {
                                     if (isNaN(id)) message.error('You should write id number of killer')
                                     else DRAGONS_API.findWithKillerWeakerThen(id)
                                         .then(data => {
                                             if (data.length !== 0) setItems(data)
                                             else message.warning(`There are no dragons with a killer weaker then killer#${id}`)
                                         })
                                 }}
                                           enterButton
                                           placeholder="id"
                             />
                         }
                >
                    <Button icon={ <LeftOutlined/> }
                            ghost={ true }
                    >
                        Find with killer weaker then
                    </Button>
                </Popover>
            )
        case 'persons':
            actions.push(
                <Popover trigger="click"
                         content={
                             <Input.Search onSearch={
                                 name => {
                                     PERSONS_API.getIncludedInName(name)
                                         .then(data => {
                                             if (data.length !== 0) setItems(data)
                                             else message.warning(`There are no persons included in name ${name}`)
                                         })
                                 }}
                                           enterButton
                                           placeholder="name"
                             />
                         }
                >
                    <Button icon={ <MehOutlined/> }
                            ghost={ true }
                    >
                        Find person included in name
                    </Button>
                </Popover>
            )
    }

    return <Layout className="site-layout">
        <Header>
            <Space>
                { actions }
            </Space>
        </Header>
        <Content>
            <Table columns={ mergedColumns }
                   rowSelection={{
                       selectedRowKeys,
                       onChange: selected => setSelectedRowKeys(selected),
                       selections: [ Table.SELECTION_ALL, Table.SELECTION_INVERT, Table.SELECTION_NONE ]
                   }}
                   dataSource={ items }
                   loading={ isLoading }
                   components={{
                       body: { cell: EditableCell }
                   }}
                   pagination={{
                       onChange: cancel,
                       position: ["bottomCenter"] }
                   }
            />
        </Content>
    </Layout>
}
EntityTable.defaultProps = { enumFields: new Map() }

export default EntityTable