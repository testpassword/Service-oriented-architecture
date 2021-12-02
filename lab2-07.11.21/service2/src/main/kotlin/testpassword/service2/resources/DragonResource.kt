package testpassword.service2.resources

import testpassword.service2.getHttpClient
import testpassword.service2.models.Dragon
import javax.ws.rs.*
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType


@Path("dragons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class DragonResource {

    enum class Type { MIN, MAX }

    @GET @Path("find_by_cave_depth")
    fun getDragonWithDeepestCave(@QueryParam("type") type: Type): List<Dragon> =
        getHttpClient()
            .target("${System.getProperty("service1_url")}/api/dragons/")
            .request(MediaType.APPLICATION_JSON)
            .get(object : GenericType<List<Dragon>>() {})
            .let {
                val extreme = it
                    .map { d -> d.coordinates.z }
                    .let { coords -> if (type == Type.MAX) coords.minOrNull() else coords.maxOrNull() }
                it.filter { d -> d.coordinates.z == extreme }
            }
}

