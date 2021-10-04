const toJson = res => {
    if (res.ok) return res.json()
    else throw new Error(res.json())
}

// change both URLs to http://localhost:9090/back-1.0-ULTIMATE/... before deploy to WAR

/* да, правильнее было бы пробрасывать корень урла через REACT_APP_ROOT_URL, но, к сожалению,
enum TypeScript должен быть известен во время сборки, поэтому переменные не подходят, а мне очень уж хотелось
попробовать enum
 */
enum EntitiesURLs {
    PERSONS = `http://localhost:9090/back-1.0-ULTIMATE/api/persons/`,
    DRAGONS = `http://localhost:9090/back-1.0-ULTIMATE/api/dragons/`
}

const EntitiesCRUD_API = {

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

export { EntitiesCRUD_API, EntitiesURLs, toJson }