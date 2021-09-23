import '../resources/App.css'
import { Layout, Menu } from "antd"
import { UserOutlined, DingdingOutlined } from '@ant-design/icons'
import React from "react"
import { EntityCRUD, EntitiesURLs } from "../api/EntityCRUD"


const ManagementConsole: React.FC = () => {

    EntityCRUD.getAll(EntitiesURLs.PERSON).then(data => console.info(data))

    return <Layout style={{ minHeight: '100vh' }}>
        <Layout.Sider>
            <Menu defaultSelectedKeys={['1']} mode="inline">
                <Menu.Item icon={<UserOutlined/>}>
                    Persons
                </Menu.Item>
                <Menu.Item icon={<DingdingOutlined/>}>
                    Dragons
                </Menu.Item>
            </Menu>
        </Layout.Sider>
        <Layout className="site-layout">
            <Layout.Content>
                <div className="site-layout-background" style={{ minHeight: 360 }}>
                </div>
            </Layout.Content>
            <Layout.Footer style={{ textAlign: "center"}}>
                <a href={"https://se.ifmo.ru/~s265570/cv/"}>
                    Kulbako Artemy 2021
                </a>
            </Layout.Footer>
        </Layout>
    </Layout>
}

export default ManagementConsole