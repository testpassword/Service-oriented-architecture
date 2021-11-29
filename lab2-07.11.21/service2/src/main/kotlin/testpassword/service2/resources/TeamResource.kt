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
    fun bindPersonToTeam(@PathParam("id") teamId: Int, @QueryParam("candidate_id") candidateId: Int): Response {
        val (status, msg) = try {
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
        }
        return Response.status(status).entity(msg).build()
    }
}