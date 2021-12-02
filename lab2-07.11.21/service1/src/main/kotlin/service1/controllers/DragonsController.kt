package service1.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import service1.directionalSort
import service1.models.Color
import service1.models.Dragon
import service1.models.Person
import service1.paginate
import service1.parseSliceParams
import service1.repos.CoordinatesRepo
import service1.repos.DragonsRepo
import service1.repos.PersonsRepo

@RestController @RequestMapping(path = ["/api/dragons/"])
class DragonsController {

    @Autowired private lateinit var repo: DragonsRepo
    @Autowired private lateinit var personsRepo: PersonsRepo
    @Autowired private lateinit var coordinatesRepo: CoordinatesRepo

    @GetMapping
    fun get(@RequestParam(required = false) limit: Int?,
            @RequestParam(required = false) offset: Int?,
            @RequestParam(required = false, defaultValue = "") sorters: String,
            @RequestParam(required = false, defaultValue = "") filters: String
    ): List<Dragon> {
        val slices = parseSliceParams(filters, sorters, Person::class)
        return repo.findAll().paginate(limit, offset).let { originals ->
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
        }
    }

    @GetMapping(path = ["grouped_by_type"])
    fun getGroupedByType(): Map<Dragon.DragonType, Int> = repo.findAll().groupingBy { it.type }.eachCount()

    @GetMapping(path = ["find_with_killer_weaker_then"])
    fun getWithKillerWeakerThen(@RequestParam killer_id: Int): List<Dragon> = repo.findByKillerLessThan(personsRepo.findById(killer_id).get())

    @PatchMapping(path = ["{id}"])
    fun modify(@PathVariable id: Int, @RequestBody changes: Dragon): Map<String,String> {
        val dragon = repo.findById(id).get()
        if (dragon.id == id) repo.save(changes)
        return mapOf("msg" to "successfully modified")
    }

    @GetMapping(path = ["{id}"])
    fun getById(@PathVariable id: Int): Dragon = repo.findById(id).get()

    @PostMapping
    fun add(@RequestBody dragon: Dragon): Int = repo.save(dragon.apply { id = null }).id!!

    @DeleteMapping(path = ["{id}"])
    fun delete(@PathVariable id: Int): Map<String, String> {
        repo.deleteById(id)
        return mapOf("msg" to "successfully deleted")
    }
}