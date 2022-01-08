// @ts-nocheck
import { Button, DatePicker, Form, Input, InputNumber, Radio } from "antd"
import React from "react"
import moment from "moment"

interface AntdColumn {
    title: string,
    key: string,
    dataIndex: string,
    editable: boolean,
    inputType: string,
    sorter: Function
}

/**
 * Creates columns for Antd.Table by template
 * @param template - object which contains column name and it's type
 */
const buildColumnsByObject = (template: object): Array<AntdColumn> =>
    Object.keys(template).map(it => ({
        title: it.toUpperCase(),
        key: it,
        dataIndex: it,
        editable: it !== 'id',
        inputType: template[it].type,
        sorter: (item1: object, item2: object) => {
            const a = item1[it]
            const b = item2[it]
            const type = template[it].type
            if (type === 'number') return a - b
            if (type === 'string') return a.length - b.length
            if (type === 'boolean') return a && b
            if (type === 'date') return new Date(a) - new Date(b)
            else return undefined
        }
    })).filter( it => it !== undefined && it !== null )

/**
 * Creates Antd.Form by template
 * @param template - object which contains column name and it's type
 * @param onFinish - action which execute on 'Submit` click
 * @param filler - fill defaultValue in form by filler object
 */
const buildCreationForm = (template: object,
                           onFinish: (any) => void,
                           filler: object = {}): JSX.Element =>
    <Form onFinish={onFinish}> {

        Object.keys(template).filter(it => it.toLowerCase() !== 'id').map(it => {
            const getInput = (templateField: object): React.FC => {
                if (templateField.type === 'enum') return <Radio.Group optionType="button"
                                                                       defaultValue={ filler[it] }
                                                                       options={ template[it].vals }/>
                if (templateField.type === 'number') return <InputNumber defaultValue={ filler[it] }/>
                if (templateField.type === 'date') return <DatePicker format={ "DD.MM.YYYY" }
                                                                      defaultValue={ moment() }/>
                if (templateField.type === 'array' && filler[it] !== undefined) return <Input defaultValue={str}/>
                else return <Input defaultValue={ filler[it] }/>
            }
            return <Form.Item label={ it.toLowerCase() }
                              name={ it }>
                { getInput( template[it]) }
            </Form.Item>
        })
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