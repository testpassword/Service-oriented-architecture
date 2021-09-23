package testpassword

import khttp.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class AdminTest {

    private val ENDPOINT = Entity.ADMIN.endpoint

    @Test fun `webapp is available`() = assertEquals(200, get(Entity.ADMIN.endpoint).statusCode)

    @Test fun `create tables in database`() = assertEquals(200, post(Entity.ADMIN.endpoint).statusCode)

    @Test fun `drop all records`() {
        delete(ENDPOINT)
        assertTrue(listOf(Entity.PERSON.endpoint, Entity.DRAGON.endpoint).flatMap { get(it).jsonArray }.isEmpty())
    }
}