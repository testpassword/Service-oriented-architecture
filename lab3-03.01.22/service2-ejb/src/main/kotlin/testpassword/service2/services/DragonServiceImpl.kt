package testpassword.service2.services

import testpassword.service2.getHttpClient
import testpassword.service2.getServices
import testpassword.service2.models.Dragon
import javax.ejb.Remote
import javax.ejb.Stateless
import javax.ws.rs.ProcessingException
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

@Stateless @Remote(DragonService::class)
open class DragonServiceImpl: DragonService {

    override infix fun getDragonWithDeepestCave(type: SORT_TYPE): List<Dragon> {
        val service = getServices().let {
            if (it.isEmpty()) throw ProcessingException("service unavailable, try later")
            else it.random()
        }
        return getHttpClient()
            .target("http://${service.getString("Address")}:${service.getInt("Port")}/api/dragons/")
            .request(MediaType.APPLICATION_JSON)
            .get(object : GenericType<List<Dragon>>() {})
            .let {
                val extreme = it
                    .map { d -> d.coordinates.z }
                    .let { coords -> if (type == SORT_TYPE.MAX) coords.maxOrNull() else coords.minOrNull() }
                it.filter { d -> d.coordinates.z == extreme }
            }
    }
}