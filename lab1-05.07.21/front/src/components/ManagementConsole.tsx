import '../resources/App.css'
import {Layout, Menu} from "antd"
import {DingdingOutlined, UserOutlined} from '@ant-design/icons'
import React, {useState} from "react"
import EntityTable from "./EntityTable"
import {EntitiesURLs} from "../api/EntityCRUD";


const ManagementConsole: React.FC = () => {

    const [table, setTable] = useState<JSX.Element>(<EntityTable entity={EntitiesURLs.PERSON}/>)

    return <Layout style={{ minHeight: '100vh' }}>
        <Layout.Sider>
            <Menu defaultSelectedKeys={['1']} mode="inline">
                <Menu.Item icon={<UserOutlined/>}
                           onClick={() => setTable(<EntityTable entity={EntitiesURLs.PERSON}/>)}
                >
                    Persons
                </Menu.Item>
                <Menu.Item icon={<DingdingOutlined/>}
                           onClick={() => setTable(<EntityTable entity={EntitiesURLs.DRAGON}/>)}
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