const ROOT = process.env.REACT_APP_BACK_URL

const toJson = res => res.json()

const EntitiesURLs = {
    PERSON: `${ROOT}persons/`,
    DRAGON: `${ROOT}dragons/`
}

const EntityCRUD = {

    getAll: <T>(entity: string): Promise<T> => {
        return fetch(entity, { method: 'GET'}).then(toJson)
    },

    getById: <T>(entity: string, id: number): Promise<T> => {
        return fetch(`${entity}${id}`, { method: 'GET' }).then(toJson)
    },

    updateById: <T>(entity: string, id: number, modifiedData: object): Promise<T> => {
        return fetch(`${entity}${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(modifiedData)
        }).then(toJson)
    },

    add: <T>(entity: string, newbie: object): Promise<T> => {
        return fetch(entity, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newbie)
        }).then(toJson)
    },

    delete: <T>(entity: string, id: number): Promise<T> => {
        return fetch(`${entity}${id}`, { method: 'DELETE' }).then(toJson)
    },
}

export { EntitiesURLs, EntityCRUD, toJson }