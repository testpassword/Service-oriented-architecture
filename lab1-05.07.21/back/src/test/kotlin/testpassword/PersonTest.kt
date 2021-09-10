package testpassword

import khttp.get
import khttp.post
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PersonTest {

    private val ENDPOINT = API.root.toString()

    @Test fun `find person included in name`() {
        val pattern = "lol"
        post(ENDPOINT, json = `generate random person`()
            .first()
            .toMutableMap()
            .apply { replace("name", get("name").toString() + pattern) }
        )
        assertNotNull(
            get(ENDPOINT)
                .jsonArray
                .toJsonObjectsList()
                .find { it.getString("name").contains(pattern) }
        )
    }
}