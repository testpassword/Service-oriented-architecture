package testpassword

import khttp.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class AdminTest {

    private val ENDPOINT = API.admin.toString()

    @Test fun `webapp is available`() = assertEquals(200, get(ENDPOINT).statusCode)

    @Test fun `drop all records`() {
        delete(ENDPOINT)
        assertTrue(listOf(API.person, API.dragon).flatMap { get(it.toString()).jsonArray }.isEmpty())
    }
}