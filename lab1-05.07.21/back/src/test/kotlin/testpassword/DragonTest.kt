package testpassword

import khttp.get
import khttp.post
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DragonTest {

    @Test fun `grouped by type`() {
        `generate random dragon`(5).forEach { post(Entity.DRAGON.endpoint, json = it) }
        assertTrue(get("${Entity.DRAGON.endpoint}grouped_by_type").statusCode in (200..299))
    }

    @Test fun `find with killer weaker then`() =
        assertTrue(
            get("${Entity.DRAGON.endpoint}find_with_killer_weaker_then",
                params = mapOf("killer_id" to 777.toString())
            ).statusCode in (200..299)
        )
}