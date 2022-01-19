package testpassword.service2.resources

import org.hibernate.ObjectNotFoundException
import testpassword.service2.models.Team
import javax.ws.rs.core.MediaType
import testpassword.service2.services.TeamServiceImpl
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("teams") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
class TeamResource {

    private val service = TeamServiceImpl()

    @GET
    fun getTeams(): Set<TeamServiceImpl.TeamDto> = service.getTeamsWithMembersIds()

    @POST
    fun createTeam(team: Team): Int = service createTeam team

    @POST @Path("{id}")
    fun bindPersonToTeam(@PathParam("id") teamIdStr: String, @QueryParam("candidate_id") candidateIdStr: String): Response {
        val (status, msg) = try {
            val teamId = teamIdStr.toInt().also { if (it <= 0) throw NumberFormatException() }
            val candidateId = candidateIdStr.toInt().also { if (it <= 0) throw NumberFormatException() }
            service.bindPersonToTeam(teamId, candidateId)
            200 to "Person $candidateId successfully bound to team $teamId"
        } catch (e: ObjectNotFoundException) {
            404 to "team or person with requested id didn't exist"
        } catch (e: NumberFormatException) {
            400 to "team_id and candidate_id should be positive Int"
        }
        return Response.status(status).entity(mapOf("msg" to msg)).build()
    }
}