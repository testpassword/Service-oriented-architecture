import { EntitiesURLs, toJson } from "./EntitiesCRUD_API"

const DRAGON_TYPE = {
    WATER: 'geekblue',
    AIR: 'blue',
    FIRE: 'volcano',
    UNKNOWN: 'magenta'
}

const DRAGONS_API = {

    getGroupedByType: <T>(): Promise<T> => fetch(`${EntitiesURLs.DRAGONS1}grouped_by_type`, { method: 'GET'}).then(toJson),

    findWithKillerWeakerThen: <T>(id: number): Promise<T> => fetch(`${EntitiesURLs.DRAGONS1}find_with_killer_weaker_then?killer_id=${id}`, { method: 'GET'}).then(toJson),

    findByCaveDepth: <T>(type: string): Promise<T> => fetch(`${EntitiesURLs.DRAGONS2}find_by_cave_depth?type=${type}`, { method: 'GET' }).then(toJson)
}

export { DRAGONS_API, DRAGON_TYPE }