import '../resources/App.css'
import { Layout, Menu } from "antd"
import { DingdingOutlined, UserOutlined } from '@ant-design/icons'
import React, { useState } from "react"
import { EntitiesURLs } from "../api/EntitiesCRUD_API"
import EntityTable from "./EntityTable"
import { COLOR } from "../api/Persons"
import { DRAGON_TYPE } from "../api/Dragons"


const ManagementConsole: React.FC = () => {
    const personsTable = <EntityTable entity={ EntitiesURLs.PERSONS } template={{
        'id': { type: 'number' },
        'name': { type: 'string' },
        'height': { type: 'number' },
        'weight': { type: 'number' },
        'passportID': { type: 'string' },
        'hairColor': {
            type: 'enum',
            vals: Object.keys(COLOR)
        }
    }}/>
    const dragonsTable = <EntityTable entity={ EntitiesURLs.DRAGONS } template={{
        'id': { type: 'number' },
        'name': { type: 'string' },
        'creationDate': { type: 'date' },
        'age': { type: 'number' },
        'wingspan': { type: 'number' },
        'color': {
            type: 'enum',
            vals: Object.keys(COLOR)
        },
        'type': {
            type: 'enum',
            vals: Object.keys(DRAGON_TYPE)
        },
        'x': { type: 'number' },
        'y': { type: 'number' },
        'killerID': { type: 'number' }
    }}/>
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