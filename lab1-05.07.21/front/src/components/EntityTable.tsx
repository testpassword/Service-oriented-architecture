import React, { useEffect, useState } from "react"
import { EntitiesURLs, EntityCRUD } from "../api/EntityCRUD"
import { Table, Input, Popconfirm, Form, Typography, Layout, Space, Button, message, Popover } from "antd"
import { Content, Header } from "antd/lib/layout/layout"
import { CloseOutlined, DeleteOutlined, PlusOutlined, SearchOutlined } from "@ant-design/icons"
import { AntdColumn, buildColumnsByObject, buildCreationForm } from "./PresentersGenerator"
import Highlighter from 'react-highlight-words'
import EditableCell from "./EditableCell"

const EntityTable: React.FC<{ entity: EntitiesURLs, template: object }> = ({ entity, template }) => {

    const [form] = Form.useForm()
    const [items, setItems] = useState<Array<object>>([])
    const [isLoading, setIsLoaded] = useState<boolean>(true)
    const [editingKey, setEditingKey] = useState(0)
    const [selectedRowKeys, setSelectedRowKeys] = useState<Array<number>>([])
    const [isFormVisible, setFormVisibility] = useState<boolean>(false)
    const [columns, setColumns] = useState<Array<object>>(buildColumnsByObject(template))
    const [searchText, setSearchText] = useState<string>('')
    const [searchedColumn, setSearchedColumn] = useState<string>('')

    const getColumnSearchProps = dataIndex => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
            <div style={{ padding: 8 }}>
                <Input placeholder={ `Search ${dataIndex}` }
                       value={ selectedKeys[0] }
                       onChange={ e => setSelectedKeys(e.target.value ? [e.target.value] : []) }
                       onPressEnter={ () => handleSearch(selectedKeys, confirm, dataIndex) }
                       style={{ marginBottom: 8, display: 'block' }}
                />
                <Space>
                    <Button type="primary"
                            icon={ <SearchOutlined/> }
                            onClick={() => handleSearch(selectedKeys, confirm, dataIndex)}
                            size="small"
                            style={{ width: 90 }}
                    >
                        Search
                    </Button>
                    <Button onClick={() => handleReset(clearFilters)}
                            size="small"
                            style={{ width: 90 }}
                    >
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

    const save = async (key) => {
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
        EntityCRUD.getAll(entity).then( data => {
            setIsLoaded(false)
            // @ts-ignore
            setItems(data.map( it => ({ ...it, key: it.id }) ))
        })
    }, [entity])

    // @ts-ignore
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
                    <Popconfirm title="Sure to cancel?" onConfirm={cancel}>
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
        // @ts-ignore
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

    return <Layout className="site-layout">
        <Header>
            <Space>
                <Popover
                    trigger="click"
                    content={
                        <div>
                            { buildCreationForm(template) }
                            <Button shape="round"
                                    icon={<CloseOutlined/>}
                                    size="small"
                                    onClick={() => setFormVisibility(false)}
                            />
                        </div>
                    }
                    visible={ isFormVisible }>
                    <Button type="primary"
                            icon={ <PlusOutlined/> }
                            ghost={ true }
                            onClick={ () => {
                                setFormVisibility(true)
                            }}
                    >
                        Add record
                    </Button>
                </Popover>
                <Button icon={ <DeleteOutlined/> }
                        ghost={ true }
                        danger
                        onClick={ () => {
                            if (selectedRowKeys.length === 0) message.warning('Nothing is selected')
                            else Promise.all(selectedRowKeys.map( it =>
                                EntityCRUD.delete(entity, it) )).then( () => {
                                // @ts-ignore
                                setItems(items.filter( it => !selectedRowKeys.includes(it.id) ))
                                message.success('All entities was deleted')
                            })
                        }}>
                    Remove
                </Button>
            </Space>
        </Header>
        <Content>
            {/*@ts-ignore*/}
            <Table columns={ mergedColumns }
                   rowSelection={{
                       selectedRowKeys,
                       /*@ts-ignore*/
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

export default EntityTable