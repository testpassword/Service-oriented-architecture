import { EntitiesURLs, toJson } from "./EntitiesCRUD_API"

const COLOR = {
    GREEN: 'green',
    YELLOW: 'yellow',
    BROWN: 'gold'
}

const PERSONS_API = {

    getIncludedInName: <T>(name: string): Promise<T> => {
        return fetch(`${EntitiesURLs.PERSONS}find_person_included_in_name?name=${name}`, { method: 'GET' }).then(toJson)
    },
}

export { PERSONS_API, COLOR }