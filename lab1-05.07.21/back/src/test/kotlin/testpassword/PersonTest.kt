package testpassword

import khttp.get
import khttp.post
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PersonTest {

    private val ENDPOINT = Entity.PERSON.endpoint

    @Test fun `find person included in name`() {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val pattern = (1..5)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        post(ENDPOINT, json = `generate random person`()
            .first()
            .toMutableMap()
            .apply { replace("name", get("name").toString() + pattern) }
        )
        assertNotNull(
            get("${ENDPOINT}find_person_included_in_name?name=${pattern}")
                .jsonArray
                .toJsonObjectsList()
                .find { it.getString("name").contains(pattern) }
        )
    }
}