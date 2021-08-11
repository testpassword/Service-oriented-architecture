package testpassword

import khttp.*
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

//TODO: тест с параметром, где параметр - базовый url (localhost:8090/api/admin)
class AdminTest {

    @Test @Order(1)
    fun `webapp is available`(): Unit = assertDoesNotThrow { get("http://localhost:8090/api/admin") }

    @Test fun `drop all records`() {
        delete("http://localhost:8090/api/admin")
        // TODO: получить от всех массивы, склеить и проверить, что пустой
        //get("http://localhost:8090/api/person")
    }
}