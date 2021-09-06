package testpassword

import khttp.delete
import khttp.get
import khttp.post
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PersonTest {

    private val ENDPOINT = API.person.toString()

    @Test fun `get all persons`() = assertDoesNotThrow { get(ENDPOINT).jsonArray }

    @Test fun `find person included in name`() {
        val pattern = "lol"
        post(ENDPOINT, json = `generate random person`()
            .first()
            .toMutableMap()
            .apply { replace("name", get("name").toString() + pattern) }
        )
        get(ENDPOINT)
    }

    @Test fun `add new person`() {
        val newbieId = post(ENDPOINT, json = `generate random person`().first()).jsonObject.getLong("id")
        assertNotNull(
            // TODO: недоделан путь и проверка
            get("${ENDPOINT}").jsonArray
                .let { it.mapIndexed { i, _ -> it.getJSONObject(i) }.toList() }
                .find { it.getLong("id") == newbieId }
        )
    }

    @Test fun `modify existing person`() = Unit

    @Test fun `remove person`() {
        // TODO: id - часть пути а не параметр
        val newbieId = post(ENDPOINT, json = `generate random person`().first()).jsonObject.getLong("id")
        delete(ENDPOINT, params = mapOf("id" to newbieId.toString()))
        println(get(ENDPOINT, params = mapOf("id" to newbieId.toString())).jsonObject)
    }
}