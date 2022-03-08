package testpassword.service2.services

import testpassword.service2.getHttpClient
import testpassword.service2.models.Dragon
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.MediaType

enum class SORT_TYPE { MIN, MAX }

open class DragonService {

    infix fun getDragonWithDeepestCave(type: SORT_TYPE): List<Dragon> =
        getHttpClient()
            .target("${System.getProperty("mule_url")}/api/dragons")
            .request(MediaType.APPLICATION_JSON)
            .get(object : GenericType<List<Dragon>>() {})
            .let {
                val extreme = it
                    .map { d -> d.coordinates.z }
                    .let { coords -> if (type == SORT_TYPE.MAX) coords.maxOrNull() else coords.minOrNull() }
                it.filter { d -> d.coordinates.z == extreme }
            }
}