package testpassword.service2.resources

import org.wildfly.naming.client.WildFlyInitialContextFactory
import testpassword.service2.services.DragonService
import testpassword.service2.services.SORT_TYPE
import testpassword.service2.services.WorkerService
import java.util.*
import javax.naming.Context
import javax.naming.InitialContext
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("dragons") @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
class DragonResource {

    private lateinit var service: DragonService

    @GET @Path("find_by_cave_depth")
    fun getDragonWithDeepestCave(@QueryParam("type") type: SORT_TYPE): Response =
        try {
            Response
                .status(200)
                .entity(
                    (InitialContext(
                        Properties().apply {
                            put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory::class.java.name) }
                    )
                        .lookup("java:global/killer-ejb/WorkerServiceImpl") as WorkerService)
                        .calculate(1, 2)
                ).build()
        } catch (e: ProcessingException) {
            Response.status(503).entity(mapOf("msg" to "service error, try later")).build()
        }
}

