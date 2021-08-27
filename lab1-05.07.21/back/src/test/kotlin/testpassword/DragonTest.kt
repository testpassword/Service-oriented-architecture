package testpassword

import khttp.get
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DragonTest {

    private val ENDPOINT = API.dragon.toString()

    @Test fun `get all dragons`() = assertDoesNotThrow { get(ENDPOINT).jsonArray }

    @Test fun `get specific dragon`() {
        /*
        добавить
        получить
         */
    }

    @Test fun `grouped by type`() = Unit

    @Test fun `find with killer weaker then`() = Unit

    @Test fun `add new dragon`() {
        /*
        добавить
        получить
         */
    }

    @Test fun `modify existing dragon`() {
        /*
        получить
        модифицировать
        получить
         */
    }

    @Test fun `remove dragon`() {
        /*
        добавить
        получить его
        удалить
        попытаться получить его
         */
    }
}