package testpassword.service2.resources

import org.hibernate.Hibernate
import org.hibernate.ObjectNotFoundException
import testpassword.service2.models.Team
import javax.ws.rs.core.MediaType
import testpassword.service2.getDBSession
import testpassword.service2.models.Person
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TeamResource {

    data class TeamDto(val id: Int, val name: String, val membersIds: List<Int?> )

    @GET
    fun getTeams(): Set<TeamDto> =
        (getDBSession().use { it.createCriteria(Team::class.java).list() } as MutableList<Team>)
            .map { TeamDto(it.id!!, it.name, it.members.map(Person::id)) }
            .toSet()

    @POST
    fun createTeam(team: Team): Int =
        team.also {
            getDBSession().use { s ->
                val trx = s.beginTransaction()
                s.save(it)
                trx.commit()
            }
        }.id!!

    @POST @Path("{id}")
    fun bindPersonToTeam(@PathParam("id") teamIdStr: String, @QueryParam("candidate_id") candidateIdStr: String): Response {
        val (status, msg) = try {
            val teamId = teamIdStr.toInt().also { if (it <= 0) throw NumberFormatException() }
            val candidateId = candidateIdStr.toInt().also { if (it <= 0) throw NumberFormatException() }
            getDBSession().use {
                val team = it.load(Team::class.java, teamId) as Team
                Hibernate.initialize(team)
                val candidate = it.load(Person::class.java, candidateId) as Person
                Hibernate.initialize(candidate)
                val trx = it.beginTransaction()
                candidate.team = team
                it.save(candidate)
                trx.commit()
            }
            200 to "Person $candidateId successfully bound to team $teamId"
        } catch (e: ObjectNotFoundException) {
            404 to "team or person with requested id didn't exist"
        } catch (e: NumberFormatException) {
            400 to "team_id and candidate_id should be positive Int"
        }
        return Response.status(status).entity(mapOf("msg" to msg)).build()
    }
}