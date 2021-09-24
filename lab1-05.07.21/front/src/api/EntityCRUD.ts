const toJson = res => res.json()

enum EntitiesURLs {
    PERSON = `http://localhost:8080/api/persons`,
    DRAGON = `http://localhost:8080/api/dragons`
}

const EntityCRUD = {

    getAll: <T>(entity: EntitiesURLs): Promise<T> => {
        return fetch(entity, { method: 'GET'}).then(toJson)
    },

    getById: <T>(entity: EntitiesURLs, id: number): Promise<T> => {
        return fetch(`${entity}${id}`, { method: 'GET' }).then(toJson)
    },

    updateById: <T>(entity: EntitiesURLs, id: number, modifiedData: object): Promise<T> => {
        return fetch(`${entity}${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(modifiedData)
        }).then(toJson)
    },

    add: <T>(entity: EntitiesURLs, newbie: object): Promise<T> => {
        return fetch(entity, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newbie)
        }).then(toJson)
    },

    delete: <T>(entity: EntitiesURLs, id: number): Promise<T> => {
        return fetch(`${entity}${id}`, { method: 'DELETE' }).then(toJson)
    },
}

export { EntitiesURLs, EntityCRUD, toJson }