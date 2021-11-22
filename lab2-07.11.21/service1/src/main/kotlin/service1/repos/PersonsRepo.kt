package service1.repos

import service1.models.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository interface PersonsRepo: CrudRepository<Person, Int> {

    fun findAllByNameContains(name: String): List<Person>
}