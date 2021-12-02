import React from "react"
import { Form, Input, InputNumber } from "antd"

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

export default EditableCell