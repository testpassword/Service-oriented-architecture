import { EntitiesURLs, toJson } from "./EntityCRUD"

const DRAGONS_API = {

    getGroupedByType: <T>(): Promise<T> => {
        return fetch(`${EntitiesURLs.DRAGONS}grouped_by_type`, { method: 'GET'}).then(toJson)
    },

    findWithKillerWeakerThen: <T>(id: number): Promise<T> => {
        return fetch(`${EntitiesURLs.DRAGONS}find_with_killer_weaker_then?killer_id=${id}`, { method: 'GET'}).then(toJson)
    }
}

export default DRAGONS_API