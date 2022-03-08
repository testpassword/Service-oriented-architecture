package testpassword.service2.resources

import testpassword.service2.services.DragonService
import testpassword.service2.services.SORT_TYPE
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("dragons") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
class DragonResource {

    private val service = DragonService()

    @GET @Path("find_by_cave_depth")
    fun getDragonWithDeepestCave(@QueryParam("type") type: SORT_TYPE): Response {
        val (code, data) = try {
            200 to service.getDragonWithDeepestCave(type)
        } catch (e: ProcessingException) {
            e.printStackTrace()
            503 to mapOf("msg" to "service error, try later")
        }
        return Response.status(code).entity(data).build()
    }
}

