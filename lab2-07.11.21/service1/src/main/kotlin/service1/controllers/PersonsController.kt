package service1.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import service1.*
import service1.models.Color
import service1.models.Person
import service1.repos.PersonsRepo

@RestController @RequestMapping(path = ["/api/persons/"])
class PersonsController {

    @Autowired private lateinit var repo: PersonsRepo

    @GetMapping
    fun get(@RequestParam(required = false) limit: Int?,
            @RequestParam(required = false) offset: Int?,
            @RequestParam(required = false, defaultValue = "") sorters: String,
            @RequestParam(required = false, defaultValue = "") filters: String
    ): List<Person> {
        val slices = parseSliceParams(filters, sorters, Person::class)
        return repo.findAll().paginate(limit, offset).let { originals ->
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
                    "passportID" -> records.directionalSort(v) { it.passportId }
                    "hairColor" -> records.directionalSort(v) { it.hairColor }
                    else -> records
                }.toMutableList()
            }
            records
        }
    }

    @GetMapping(path = ["find_person_included_in_name"])
    fun getIncludedInName(@RequestParam name: String): List<Person> = repo.findAllByNameContains(name)

    @PutMapping(path = ["{id}"])
    fun modify(@PathVariable id: Int, @RequestBody changes: Person): String {
        val person = repo.findById(id).get()
        if (person.id == id) repo.save(changes)
        return "successfully modified"
    }

    @GetMapping(path = ["{id}"])
    fun getById(@PathVariable id: Int): Person = repo.findById(id).get()

    @PostMapping
    fun add(@RequestBody person: Person): Int = repo.save(person.apply { id = null }).id!!

    @DeleteMapping(path = ["{id}"])
    fun delete(@PathVariable id: Int): String {
        repo.deleteById(id)
        return "successfully deleted"
    }
}