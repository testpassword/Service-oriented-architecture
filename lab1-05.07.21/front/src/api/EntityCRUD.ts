const ROOT = process.env.REACT_APP_BACK_URL

const DEFAULT_HANDLER = res => {
    return res.json()
}

const EntitiesURLs = {
    PERSON: `${ROOT}persons/`,
    DRAGON: `${ROOT}dragons/`
}

const EntityCRUD = {

    getAll: <T>(entity: string): Promise<T> => {
        alert(entity)
        return fetch(entity, { method: 'GET'}).then(DEFAULT_HANDLER)
    },

    getById: <T>(entity: string, id: number): Promise<T> => {
        return fetch(`${entity}${id}`, { method: 'GET' }).then(DEFAULT_HANDLER)
    },

    updateById: <T>(entity: string, id: number, modifiedData: object): Promise<T> => {
        return fetch(`${entity}${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(modifiedData)
        }).then(DEFAULT_HANDLER)
    },

    add: <T>(entity: string, newbie: object): Promise<T> => {
        return fetch(entity, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newbie)
        }).then(DEFAULT_HANDLER)
    },

    delete: <T>(entity: string, id: number): Promise<T> => {
        return fetch(`${entity}${id}`, { method: 'DELETE' }).then(DEFAULT_HANDLER)
    },
}

export { EntitiesURLs, EntityCRUD, DEFAULT_HANDLER }