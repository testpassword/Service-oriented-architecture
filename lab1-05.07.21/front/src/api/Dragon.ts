import { EntitiesURLs, toJson } from "./EntityCRUD"

const DRAGON_API = {

    getGroupedByType: <T>(): Promise<T> => {
        return fetch(`${EntitiesURLs.DRAGON}grouped_by_type`, { method: 'GET'}).then(toJson)
    },

    findWithKillerWeakerThen: <T>(id: number): Promise<T> => {
        return fetch(`${EntitiesURLs.DRAGON}find_with_killer_weaker_then?killer_id=${id}`, { method: 'GET'}).then(toJson)
    }
}

export default DRAGON_API