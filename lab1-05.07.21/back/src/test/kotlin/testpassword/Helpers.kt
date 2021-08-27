package testpassword

import com.github.javafaker.Faker
import testpassword.models.Color
import java.net.URL

val F = Faker()
val API_ROOT = URL("http://localhost:8090/api/")
data class WebAppMap(
    val root: URL = API_ROOT,
    val admin: URL = URL("${API_ROOT}admin"),
    val person: URL = URL("${API_ROOT}person"),
    val dragon: URL = URL("${API_ROOT}dragon"),
)
val API = WebAppMap()

fun `generate random person`(amount: Int = 1) =
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
            "color" to Color.values().random()
        )
    }