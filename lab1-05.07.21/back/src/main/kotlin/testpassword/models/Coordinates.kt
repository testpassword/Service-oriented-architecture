package testpassword.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.json.JSONObject
import testpassword.repos.CoordinatesTable
import kotlin.math.pow

class Coordinates(id: EntityID<Long>): LongEntity(id), Comparable<Coordinates>, JSONRecoverable<Coordinates> {

    companion object: LongEntityClass<Coordinates>(CoordinatesTable)
    var x by CoordinatesTable.x
    var y by CoordinatesTable.y

    override infix operator fun compareTo(other: Coordinates): Int {
        val hypotenuse: (Coordinates) -> Double = { x.pow(2) + y.pow(2) }
        return (hypotenuse(this) - hypotenuse(other)).toInt()
    }

    override infix fun recoverFromJSON(changes: JSONObject): Coordinates =
        also {
            listOf(
                { x = changes.getDouble("x") },
                { y = changes.getDouble("y") }
            ).forEach { runCatching(it) }
        }

    override fun transformToJSON(): JSONObject =
        JSONObject().apply {
            put("id", id)
            put("x", x)
            put("y", y)
        }
}