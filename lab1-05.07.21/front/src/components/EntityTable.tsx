// @ts-nocheck
import React, { useEffect, useState } from "react"
import { EntitiesURLs, EntitiesCRUD_API } from "../api/EntitiesCRUD_API"
import { Table, Input, Typography, Layout, Space, Button, message, Popover, Tag } from "antd"
import { Content, Header } from "antd/lib/layout/layout"
import { CloseOutlined, DeleteOutlined, PlusOutlined, SearchOutlined, DeleteColumnOutlined, LeftOutlined, MehOutlined } from "@ant-design/icons"
import { AntdColumn, buildColumnsByObject, buildCreationForm } from "./PresentersGenerator"
import Highlighter from 'react-highlight-words'
import EditableCell from "./EditableCell"
import { DRAGON_TYPE, DRAGONS_API } from "../api/Dragons"
import { PERSONS_API } from "../api/Persons"

const EntityTable: React.FC<{ entity: EntitiesURLs, template: object }> =
    ({ entity, template }) => {

    const columns = buildColumnsByObject(template)
    const [items, setItems] = useState<Array<object>>([])
    const [isLoading, setIsLoaded] = useState<boolean>(true)
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
                            onClick={ () => handleSearch(selectedKeys, confirm, dataIndex) }
                    >
                        Search
                    </Button>
                    <Button onClick={ () => handleReset(clearFilters) }>
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

    useEffect(() => {
        EntitiesCRUD_API.getAll(entity)
            .then( formData => {
                setIsLoaded(false)
                setItems(formData.map( it => {
                    let expanded = { ...it, key: it.id }
                    if (entity.split('/').slice(-2)[0] === 'dragons') {
                        expanded['x'] = it.coordinates.x
                        expanded['y'] = it.coordinates.y
                        delete expanded.coordinates
                    }
                    return expanded
                })
                )
            }).catch( err => message.error(err) )
    }, [entity])

    const mergedColumns = [...columns.map( (col: AntdColumn) => ({ ...col, ...getColumnSearchProps(col.dataIndex) })), {
        title: "ACTIONS",
        dataIndex: "actions",
        fixed: 'right',
        render: ( _, record ) =>
            <Typography.Link>
                <Popover content={ buildCreationForm(template, formData => {
                    const filteredFormData = JSON.parse(JSON.stringify(formData)) // быстро удалить все пустые пары ключ-значение из объекта
                    if (Object.keys(filteredFormData).length !== 0) {
                        EntitiesCRUD_API.updateById(entity, record.id, filteredFormData)
                            .then( answer => {
                                setItems([...items.filter(it => it.id !== record.id), {...record, ...filteredFormData}])
                                message.success(answer.msg)
                            })
                            .catch( err => message.error(err.message) )
                    } else message.warning('Nothing to modify')
                }, [], record) }>
                    Edit
                </Popover>
            </Typography.Link>
    }]

    const actions = [
        <Popover
            content={
                <div>{
                    buildCreationForm(template, formData => {
                        EntitiesCRUD_API.add(entity, formData)
                            .then( answer => {
                                setItems([...items, {...formData, id: answer.id}])
                                setFormVisibility(false)
                            })
                            .catch( err => message.error(err) )
                    })
                }
                <Button shape="round"
                        danger
                        icon={ <CloseOutlined/> }
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
            break
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
            break
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
                       position: ["bottomCenter"] }
                   }
            />
        </Content>
    </Layout>
}
EntityTable.defaultProps = { enumFields: new Map() }

export default EntityTable