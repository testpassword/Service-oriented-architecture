package service1.repos

import service1.models.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import service1.models.Dragon

@Repository interface DragonsRepo: CrudRepository<Dragon, Int> {

    fun findByKiller(killer: Person): List<Dragon>

    fun findByKillerLessThan(killer: Person): List<Dragon>
}