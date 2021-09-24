import { Button, Form, Input, InputNumber } from "antd"
import React from "react"

interface AntdColumn {
    title: string,
    key: string,
    dataIndex: string,
    editable: boolean,
    inputType: string,
    sorter: Function
}

const buildColumnsByObject = (template: object): Array<AntdColumn> =>
    Object.keys(template).map(it => ({
        title: it.toUpperCase(),
        key: it,
        dataIndex: it,
        editable: it !== 'id',
        inputType: template[it],
        sorter: (item1: object, item2: object) => {
            const a = item1[it]
            const b = item2[it]
            const isParamsTypesEquals = (type: string): boolean => [a, b].every(p => typeof p === type)
            if (isParamsTypesEquals('number')) return a - b
            if (isParamsTypesEquals('string')) return a.length - b.length
            if (isParamsTypesEquals('boolean')) return a && b
            else return undefined
        }
    })).filter( it => it !== undefined && it !== null )

const buildCreationForm = (template: object, requiredFields: Array<string> = []): JSX.Element =>
    <Form>
        {
            Object.keys(template).map( it =>
                <Form.Item label={ it.toLowerCase() }
                           name={ it }
                           rules={[{ required: requiredFields.includes(it) }]}
                >
                    { (template[it] === 'number') ? <InputNumber/> : <Input/> }
                </Form.Item>)
        }
        <Form.Item>
            <Button type="primary"
                    htmlType="submit">
                Submit
            </Button>
        </Form.Item>
    </Form>

export { buildColumnsByObject, buildCreationForm }
export type { AntdColumn }