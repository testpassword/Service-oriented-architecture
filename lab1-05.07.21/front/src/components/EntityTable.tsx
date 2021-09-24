import React, { useEffect, useState } from "react"
import { EntitiesURLs, EntityCRUD } from "../api/EntityCRUD"
import { Table, Input, InputNumber, Popconfirm, Form, Typography } from "antd"

const EditableCell = ({ editing, dataIndex, title, inputType, record, index, children, ...restProps }) =>
    <td {...restProps}>
        {editing ?
            <Form.Item
                name={dataIndex}
                style={{ margin: 0 }}
                rules={[{ required: true, message: `Please Input ${title}!` }]}
            >
                { inputType === "number" ? <InputNumber /> : <Input /> }
            </Form.Item>
            : children
        }
    </td>

const EntityTable: React.FC<{ entity: EntitiesURLs }> = ({ entity }) => {

    const [form] = Form.useForm()
    const [items, setItems] = useState<Array<object>>([])
    const [columns, setColumns] = useState<Array<object>>([])
    const [isLoading, setIsLoaded] = useState<boolean>(true)
    const [editingKey, setEditingKey] = useState(0)

    const isEditing = (record): boolean => record.key === editingKey

    const cancel = () => setEditingKey(0)

    const edit = (record) => {
        form.setFieldsValue(record)
        setEditingKey(record.key)
    }

    const save = async (key) => {
        try {
            const row = await form.validateFields();
            const newData = [...columns];
            // @ts-ignore
            const index = newData.findIndex((item) => key === item.key);
            if (index > -1) {
                const item = newData[index];
                newData.splice(index, 1, { ...item, ...row });
                setItems(newData);
                setEditingKey(0);
            } else {
                newData.push(row);
                setItems(newData);
                setEditingKey(0);
            }
        } catch (errInfo) {
            console.log("Validate Failed:", errInfo);
        }
    }

    const buildColumnsByObject = (obj: object): Array<object> =>
        [...Object.keys(obj).map(it => ({
            title: it.toUpperCase(),
            key: it,
            dataIndex: it,
            editable: it !== 'id',
            sorter: (item1: object, item2: object) => {
                const a = item1[it]
                const b = item2[it]
                const isParamsTypesEquals = (type: string): boolean => [a, b].every(p => typeof p === type)
                if (isParamsTypesEquals('number')) return a - b
                if (isParamsTypesEquals('string')) return a.length - b.length
                if (isParamsTypesEquals('boolean')) return a && b
                else return undefined
            }
        })).filter( it => it !== undefined && it !== null ), {
            title: "actions",
            dataIndex: "actions",
            render: ( _, record ) =>
                isEditing(record) ?
                    <span>
                        <a onClick={() => save(record.key)}
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
    }]

    // @ts-ignore
    useEffect(() => {
        EntityCRUD.getAll(entity).then(data => {
            setIsLoaded(false)
            // @ts-ignore
            setItems(data.map( it => ({ ...it, key: it.id }) ))
            // @ts-ignore
            setColumns(buildColumnsByObject(data[0]))
        })
    }, [buildColumnsByObject, entity])

    const mergedColumns = columns.map((col) => {
        // @ts-ignore
        return !col.editable
            ? col
            : {
                ...col,
                onCell: (record) => ({
                    record,
                    // @ts-ignore
                    inputType: col.dataIndex === "age" ? "number" : "text",
                    // @ts-ignore
                    dataIndex: col.dataIndex,
                    // @ts-ignore
                    title: col.title,
                    editing: isEditing(record)
                })
            }
    })
    return <Form form={ form } component={ false }>
        <Table columns={ mergedColumns }
               dataSource={ items }
               loading={ isLoading }
               components={{
                   body: { cell: EditableCell }
               }}
               pagination={{ onChange: cancel }}
        />
    </Form>
}

export default EntityTable