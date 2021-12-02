import { EntitiesURLs, toJson } from "./EntitiesCRUD_API"

const TEAMS_API = {

    bindPersonToTeam: <T>(id: number, candidateId: number): Promise<T> => fetch(`${EntitiesURLs.TEAMS}${id}?candidate_id=${candidateId}`, { method: 'POST'}).then(toJson),
}

export { TEAMS_API }