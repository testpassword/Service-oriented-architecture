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
import testpassword.models.Dragon
import testpassword.paginate
import testpassword.parseSliceParams
import testpassword.repos.CoordinatesRepo
import testpassword.repos.DragonsRepo
import testpassword.repos.PersonsRepo

@Endpoint class DragonsEndpoint @Autowired
constructor(
    private val repo: DragonsRepo,
    private val personsRepo: PersonsRepo,
    private val coordinatesRepo: CoordinatesRepo
    ) {

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDragonsRequest") @ResponsePayload
    fun get(@RequestPayload req: GetDragonsRequest) =
        GetDragonsResponse().apply {
            val slices = parseSliceParams(req.filters ?: "", req.sorters ?: "", Dragon::class)
            dtos.addAll(
                repo.findAll().paginate(req.limit, req.offset).let { originals ->
                    var records = originals.toMutableList()
                    slices.first.forEach { k, v ->
                        records.retainAll {
                            when (k) {
                                "name" -> it.name.contains(v)
                                "coordinates" -> it.coordinates.id == coordinatesRepo.findById(v.toInt()).get().id
                                "creationDate" -> it.creationDate.time == v.toLong()
                                "age" -> it.age == v.toInt()
                                "wingspan" -> it.wingspan == v.toDouble()
                                "color" -> it.color == Color.valueOf(v)
                                "type" -> it.type == Dragon.DragonType.valueOf(v)
                                "killerId" -> it.killer?.id == personsRepo.findById(v.toInt()).get().id
                                else -> throw HttpMessageNotReadableException("")
                            }
                        }
                    }
                    slices.second.forEach { k, v ->
                        records = when (k) {
                            "name" -> records.directionalSort(v) { it.name }
                            "coordinates" -> records.directionalSort(v) { it.coordinates }
                            "creationDate" -> records.directionalSort(v) { it.creationDate }
                            "age" -> records.directionalSort(v) { it.age }
                            "wingspan" -> records.directionalSort(v) { it.wingspan }
                            "color" -> records.directionalSort(v) { it.color }
                            "type" -> records.directionalSort(v) { it.type }
                            "killerID" -> records.directionalSort(v) { it.killer }
                            else -> records
                        }.toMutableList()
                    }
                    records
                }.map { Dragon toDto it }
            )
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDragonsGroupedByTypeRequest") @ResponsePayload
    fun getGroupedByType(@RequestPayload req: GetDragonsGroupedByTypeRequest) =
        GetDragonsGroupedByTypeResponse().apply {
            stat.addAll(
                repo
                    .findAll()
                    .groupingBy { it.type }
                    .eachCount()
                    .map {
                        DragonStat().apply {
                            type = it.key.toString()
                            count = it.value
                        }
                    }
            )
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDragonsWithKillerWeakerThenRequest") @ResponsePayload
    fun getWithKillerWeakerThen(@RequestPayload req: GetDragonsWithKillerWeakerThenRequest) =
        GetDragonsWithKillerWeakerThenResponse().apply {
            dtos.addAll(repo.findByKillerLessThan(personsRepo.findById(req.killerId).get()).map { Dragon toDto it })
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifyDragonByIdRequest") @ResponsePayload
    fun modify(@RequestPayload req: ModifyDragonByIdRequest) =
        ModifyDragonByIdResponse().apply {
            val changes = Dragon fromDto req.dragon
            changes.id?.let { repo.save(changes) }
            msg = "successfully modified"
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDragonByIdRequest") @ResponsePayload
    fun getById(@RequestPayload req: GetDragonByIdRequest) =
        GetDragonByIdResponse().apply {
            dragon = Dragon toDto repo.findById(req.id).get()
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addDragonRequest") @ResponsePayload
    fun add(@RequestPayload req: AddDragonRequest) =
        AddDragonResponse().apply {
            id = repo.save(Dragon fromDto req.dragon).id!!
        }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteDragonByIdRequest") @ResponsePayload
    fun delete(@RequestPayload req: DeleteDragonByIdRequest) =
        DeleteDragonByIdResponse().apply {
            repo.deleteById(req.id)
            msg = "successfully deleted"
        }
}