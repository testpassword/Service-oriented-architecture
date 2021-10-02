package testpassword.repos

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import testpassword.extensions.*
import testpassword.models.*
import java.time.LocalDateTime
import kotlin.random.Random

object DragonTable: LongIdTable("dragon") {
    val name = text("name").check(op = isNotBlank)
    val coordinates = reference("coordinates", CoordinatesTable.id).default(
            Coordinates.new {
                x = Random.nextDouble(until = 1000.0)
                y = Random.nextDouble(until = 1000.0)
            }.id
    )
    val creationDate = datetime("creation_date").default(LocalDateTime.now())
    val age = integer("age").check(op = isPositiveInt)
    val wingspan = double("wingspan").check(op = isPositiveDouble)
    val color = enumeration("color", Color::class)
    val type = enumeration("type", Dragon.DragonType::class).default(Dragon.DragonType.UNKNOWN)
    val killer = reference("killerID", PersonTable.id).nullable()

    fun groupByType(): Map<Dragon.DragonType, Int> = Dragon.all().groupingBy { it.type }.eachCount()

    infix fun `find with killer weaker then`(candidate: Person): Set<Dragon> =
        Dragon.all().filter { it.killer != null }.filter { it.killer!! < candidate }.toSet()
}