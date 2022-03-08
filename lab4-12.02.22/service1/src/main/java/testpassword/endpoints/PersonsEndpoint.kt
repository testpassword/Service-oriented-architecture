package testpassword.endpoints

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import testpassword.NAMESPACE_URI
import testpassword.directionalSort
import testpassword.dtos.*
import testpassword.models.Color
import testpassword.models.Person
import testpassword.paginate
import testpassword.parseSliceParams
import testpassword.repos.PersonsRepo

@Endpoint class PersonsEndpoint @Autowired constructor(private val repo: PersonsRepo) {

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonsRequest") @ResponsePayload
    fun get(@RequestPayload req: GetPersonsRequest) =
        GetPersonsResponse().apply {
            val slices = parseSliceParams(req.filters ?: "", req.sorters ?: "", Person::class)
            dtos.addAll(
                repo.findAll().paginate(req.limit, req.offset).also { println("LOL ${it.count()}") }.let { originals ->
                    var records = originals.toMutableList()
                    slices.first.forEach { k, v ->
                        records.retainAll {
                            when (k) {
                                "name" -> it.name.contains(v)
                                "height" -> it.height == v.toInt()
                                "weight" -> it.weight == v.toInt()
                                "passportId" -> it.passportId.contains(v)
                                "hairColor" -> it.hairColor == Color.valueOf(v)
                                else -> throw HttpMessageNotReadableException("")
                            }
                        }
                    }
                    slices.second.forEach { k, v ->
                        records = when (k) {
                            "name" -> records.directionalSort(v) { it.name }
                            "height" -> records.directionalSort(v) { it.height }
                            "weight" -> records.directionalSort(v) { it.weight }
                            "passportId" -> records.directionalSort(v) { it.passportId }
                            "hairColor" -> records.directionalSort(v) { it.hairColor }
                            else -> records
                        }.toMutableList()
                    }
                    records
                }.map { Person toDto it }).also { println("KEK: ${dtos.count()}") }
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonsIncludedInNameRequest") @ResponsePayload
    fun getIncludedInName(@RequestPayload req: GetPersonsIncludedInNameRequest) =
        GetPersonsIncludedInNameResponse().apply {
            dtos.addAll(repo.findAllByNameContains(req.name).map { Person toDto it }).apply { println("""KEK ${dtos.count()}""") }
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifyPersonByIdRequest") @ResponsePayload
    fun modify(@RequestPayload req: ModifyPersonByIdRequest) =
        ModifyPersonByIdResponse().apply {
            val changes = Person fromDto req.person
            changes.id?.let { repo.save(changes) }
            msg = "successfully modified"
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonByIdRequest") @ResponsePayload
    fun getById(@RequestPayload req: GetPersonByIdRequest) =
        GetPersonByIdResponse().apply {
            person = Person toDto repo.findById(req.id).get()
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPersonRequest") @ResponsePayload
    fun add(@RequestPayload req: AddPersonRequest) =
        AddPersonResponse().apply {
            id = repo.save(Person fromDto req.person).id!!
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePersonByIdRequest") @ResponsePayload
    fun delete(@RequestPayload req: DeletePersonByIdRequest) =
        DeletePersonByIdResponse().apply {
            repo.deleteById(req.id)
            msg = "successfully deleted"
    }
}