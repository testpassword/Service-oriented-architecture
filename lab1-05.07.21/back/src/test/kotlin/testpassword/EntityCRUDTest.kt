package testpassword

import khttp.*
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class EntityCRUDTest {

    enum class Entity(val endpoint: String) {
        PERSON("http://localhost:8090/api/persons/"),
        DRAGON("http://localhost:8090/api/dragons/");
    }

    private fun `generate entity`(entity: Entity): Map<String, Any> =
        when (entity) {
            Entity.PERSON -> `generate random person`()
            Entity.DRAGON -> `generate random dragon`()
        }.first()

    @ParameterizedTest @EnumSource(Entity::class)
    fun `get all`(entity: Entity) {
        Assertions.assertDoesNotThrow { get(entity.endpoint).jsonArray }
    }


    @ParameterizedTest @EnumSource(Entity::class)
    fun `add new`(entity: Entity) {
        val newbieId = post(entity.endpoint, json = `generate entity`(entity)).id
        Assertions.assertEquals(newbieId, get("${entity.endpoint}$newbieId").id)
    }

    @ParameterizedTest @EnumSource(Entity::class)
    fun `modify existing`(entity: Entity) {
        val extendedEndpoint = "${entity.endpoint}${post(entity.endpoint, json = `generate entity`(entity)).id}"
        val getAndConvert: () -> JSONObject = { get(extendedEndpoint).jsonObject }
        val orig = getAndConvert()
        put(extendedEndpoint, json = mapOf("age" to 5))
        val modified = getAndConvert()
        Assertions.assertNotEquals(orig, modified)
    }

    @ParameterizedTest @EnumSource(Entity::class)
    fun remove(entity: Entity) {
        val extendedEndpoint = "${entity.endpoint}${post(entity.endpoint, json = `generate entity`(entity)).id}"
        delete(extendedEndpoint)
        Assertions.assertEquals(404, get(extendedEndpoint).statusCode)
    }
}