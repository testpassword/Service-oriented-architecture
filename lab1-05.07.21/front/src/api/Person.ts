import { EntitiesURLs, toJson } from "./EntityCRUD"

const PERSON_API = {

    getIncludedInName: <T>(name: string): Promise<T> => {
        return fetch(`${EntitiesURLs.PERSON}find_person_included_in_name?name=${name}`, { method: 'GET' }).then(toJson)
    },
}

export default PERSON_API