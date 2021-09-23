import { EntitiesURLs, DEFAULT_HANDLER } from "./EntityCRUD"

const DRAGON_API = {

    getGroupedByType: <T>(): Promise<T> => {
        return fetch(`${EntitiesURLs.DRAGON}grouped_by_type`, { method: 'GET'}).then(DEFAULT_HANDLER)
    },

    findWithKillerWeakerThen: <T>(id: number): Promise<T> => {
        return fetch(`${EntitiesURLs.DRAGON}find_with_killer_weaker_then?killer_id=${id}`, { method: 'GET'}).then(DEFAULT_HANDLER)
    }
}

export default DRAGON_API