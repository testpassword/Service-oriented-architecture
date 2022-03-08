package testpassword.service2.services

import org.hibernate.Hibernate
import testpassword.service2.getDBSession
import testpassword.service2.models.Person
import testpassword.service2.models.Team
import java.io.Serializable

data class TeamDto(val id: Int, val name: String, val membersIds: List<Int?>): Serializable

open class TeamService {

    fun getTeamsWithMembersIds(): Set<TeamDto> =
        getDBSession().use {
            it.createQuery(
                it.criteriaBuilder
                    .createQuery(Team::class.java)
                    .apply { from(Team::class.java) }
            ).resultList
        }
            .map { TeamDto(it.id!!, it.name, it.members.map(Person::id)) }
            .toSet()

    infix fun createTeam(team: Team): Int =
        team.also {
            getDBSession().use { s ->
                val trx = s.beginTransaction()
                s.save(it)
                trx.commit()
            }
        }.id!!

    fun bindPersonToTeam(teamId: Int, personId: Int) =
        getDBSession().use {
            val team = it.load(Team::class.java, teamId) as Team
            Hibernate.initialize(team)
            val candidate = it.load(Person::class.java, personId) as Person
            Hibernate.initialize(candidate)
            val trx = it.beginTransaction()
            candidate.team = team
            it.save(candidate)
            trx.commit()
        }
}