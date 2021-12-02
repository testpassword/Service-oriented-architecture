const toJson = res => {
    if (res.ok) return res.json()
    else throw new Error(res.json())
}

const SERVICE1_URL = process.env.REACT_APP_SERVICE1_URL
const SERVICE2_URL = process.env.REACT_APP_SERVICE2_URL


const EntitiesURLs = {
    PERSONS: `${SERVICE1_URL}/api/persons/`,
    DRAGONS1: `${SERVICE1_URL}/api/dragons/`,
    DRAGONS2: `${SERVICE2_URL}/api/dragons/`,
    TEAMS: `${SERVICE2_URL}/api/teams/`
}

const EntitiesCRUD_API = {

    getAll: <T>(entity: object): Promise<T> => fetch(`${entity}`, { method: 'GET'}).then(toJson),

    getById: <T>(entity: object, id: number): Promise<T> => fetch(`${entity}${id}`, { method: 'GET' }).then(toJson),

    updateById: <T>(entity: object, id: number, modified: object): Promise<T> =>
        fetch(`${entity}${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(modified)
        }).then(toJson),

    add: <T>(entity: object, newbie: object): Promise<T> =>
        fetch(`${entity}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newbie)
        }).then(toJson),

    delete: <T>(entity: object, id: number): Promise<T> => fetch(`${entity}${id}`, { method: 'DELETE' }).then(toJson),
}

export { EntitiesCRUD_API, EntitiesURLs, toJson }