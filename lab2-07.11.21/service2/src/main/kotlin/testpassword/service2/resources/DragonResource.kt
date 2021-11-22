package testpassword.service2.resources

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
class DragonResource {
    @GET
    fun hello() : Response {
        return Response.ok().entity("test").build()
    }
}