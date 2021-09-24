import '../resources/App.css'
import {Layout, Menu} from "antd"
import { DingdingOutlined, UserOutlined } from '@ant-design/icons'
import React, {useState} from "react"
import { EntitiesURLs } from "../api/EntityCRUD"
import { EntityTable } from "./EntityTable"
import { buildColumnsByObject } from "./PresentersGenerator"


const ManagementConsole: React.FC = () => {
    const personsTable = <EntityTable entity={ EntitiesURLs.PERSONS } columns={ buildColumnsByObject({
        'id': 'number',
        'name': 'string',
        'height': 'number',
        'weight': 'number',
        'passportID': 'string',
        'hairColor': 'string'
    })}/>
    const dragonsTable = <EntityTable entity={ EntitiesURLs.DRAGONS } columns={ buildColumnsByObject({
        'id': 'number',
        'name': 'string',
        'creationDate': 'string',
        'age': 'number',
        'wingspan': 'number',
        'color': 'string',
        'type': 'string',
        'killer_id': 'number'
    })}/>
    const [table, setTable] = useState<JSX.Element>(personsTable)
    const [menuIsCollapsed, setMenuCollapsed] = useState<boolean>(false)
    return <Layout style={{ minHeight: '100vh' }}>
        <Layout.Sider collapsible
                      collapsed={ menuIsCollapsed }
                      onCollapse={ () => setMenuCollapsed(!menuIsCollapsed) }
        >
            <Menu defaultSelectedKeys={['1']} mode="inline">
                <Menu.Item icon={<UserOutlined/>}
                           onClick={() => setTable(personsTable)}
                >
                    Persons
                </Menu.Item>
                <Menu.Item icon={<DingdingOutlined/>}
                           onClick={() => setTable(dragonsTable)}
                >
                    Dragons
                </Menu.Item>
            </Menu>
        </Layout.Sider>
        <Layout className="site-layout">
            <Layout.Content>
                <div className="site-layout-background" style={{ minHeight: 360 }}>
                    { table }
                </div>
            </Layout.Content>
            <Layout.Footer style={{ textAlign: "center"}}>
                <a href={ "https://se.ifmo.ru/~s265570/cv/" }>
                    Kulbako Artemy 2021
                </a>
            </Layout.Footer>
        </Layout>
    </Layout>
}

export default ManagementConsole