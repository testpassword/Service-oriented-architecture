import React, { useEffect, useState } from "react"
import { EntitiesURLs, EntityCRUD } from "../api/EntityCRUD"
import { Table, Input, InputNumber, Popconfirm, Form, Typography, Layout, Space, Button, message } from "antd"
import { Content, Header } from "antd/lib/layout/layout"
import { DeleteOutlined } from "@ant-design/icons"
import { AntdColumn } from "./PresentersGenerator"

const EditableCell: React.FC<{
    editing: boolean, dataIndex: string, title: string, inputType: string, record: object, index: number }> =
    ({ editing, dataIndex, title, inputType, record, index, children, ...restProps }) =>
    <td {...restProps}>
        {editing ?
            <Form.Item
                name={ dataIndex }
                style={{ margin: 0 }}
                rules={[{ required: true, message: `Please Input ${title}!` }]}
            >
                { inputType === "number" ? <InputNumber /> : <Input /> }
            </Form.Item>
            : children
        }
    </td>

const EntityTable: React.FC<{ entity: EntitiesURLs, columns: Array<object> }> =
    ({ entity, columns }) => {

    const [form] = Form.useForm()
    const [items, setItems] = useState<Array<object>>([])
    const [isLoading, setIsLoaded] = useState<boolean>(true)
    const [editingKey, setEditingKey] = useState(0)
    const [selectedRowKeys, setSelectedRowKeys] = useState([])

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

    // @ts-ignore
    useEffect(() => {
        EntityCRUD.getAll(entity).then( data => {
            setIsLoaded(false)
            // @ts-ignore
            setItems(data.map( it => ({ ...it, key: it.id }) ))
        })
    }, [entity])

    // @ts-ignore
    const mergedColumns = [...columns, {
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
            <Space size={"middle"}>
                <Button icon={<DeleteOutlined/>}
                        ghost={ true }
                        danger
                        onClick={ () => {
                            if (selectedRowKeys.length === 0) message.warning('Nothing is selected')
                            else Promise.all(selectedRowKeys.map( it =>
                                EntityCRUD.delete(entity, it) )).then( () => {
                                    // @ts-ignore
                                    setItems(items.filter( it => !selectedRowKeys.includes(it.id) ))
                                    message.success('All entities was deleted')
                                }
                            )
                        } }>
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

export { EntityTable }