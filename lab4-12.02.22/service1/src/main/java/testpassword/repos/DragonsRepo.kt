package testpassword.repos

import testpassword.models.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import testpassword.models.Dragon

@Repository interface DragonsRepo: CrudRepository<Dragon, Int> {

    fun findByKiller(killer: Person): List<Dragon>

    fun findByKillerLessThan(killer: Person): List<Dragon>
}