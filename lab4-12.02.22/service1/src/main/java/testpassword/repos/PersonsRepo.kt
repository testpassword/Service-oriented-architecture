package testpassword.repos

import testpassword.models.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository interface PersonsRepo: CrudRepository<Person, Int> {

    fun findAllByNameContains(name: String): List<Person>
}