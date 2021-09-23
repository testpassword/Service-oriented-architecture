package testpassword

import com.github.javafaker.Faker
import khttp.responses.Response
import org.json.JSONArray
import testpassword.models.Color
import testpassword.models.Dragon

enum class Entity(val endpoint: String) {
    ADMIN("http://localhost:8090/api/admin/"),
    PERSON("http://localhost:8090/api/persons/"),
    DRAGON("http://localhost:8090/api/dragons/");
}

fun JSONArray.toJsonObjectsList() = this.let { it.mapIndexed { i, _ -> it.getJSONObject(i) }.toList() }

val Response.id: Long get() = this.jsonObject.getLong("id")

val F = Faker()
fun `generate random person`(amount: Int = 1): List<Map<String, Any>> =
    (1..amount).map {
        mapOf(
            "name" to F.name().firstName(),
            "height" to F.number().numberBetween(0, 100),
            "weight" to F.number().numberBetween(1, 100),
            "passportID" to F.superhero().name(),
            "hairColor" to Color.values().random()
        )
    }

fun `generate random dragon`(amount: Int = 1) =
    (1..amount).map {
        mapOf(
            "name" to F.slackEmoji().emoji(),
            "age" to F.number().numberBetween(0, 100),
            "wingspan" to F.number().randomDouble(2, 0, 100),
            "color" to Color.values().random(),
            "type" to Dragon.DragonType.values().random()
        )
    }