import { EntitiesURLs, toJson } from "./EntityCRUD"

const PERSONS_API = {

    getIncludedInName: <T>(name: string): Promise<T> => {
        return fetch(`${EntitiesURLs.PERSONS}find_person_included_in_name?name=${name}`, { method: 'GET' }).then(toJson)
    },
}

export default PERSONS_API