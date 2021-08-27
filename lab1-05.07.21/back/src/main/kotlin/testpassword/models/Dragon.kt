package testpassword.models

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.json.JSONObject
import testpassword.repos.DragonTable
import java.time.LocalDateTime

class Dragon(id: EntityID<Long>): LongEntity(id), Comparable<Dragon>, JSONRecoverable<Dragon> {
    companion object: LongEntityClass<Dragon>(DragonTable)
    var name by DragonTable.name
    var coordinates by Coordinates referencedOn DragonTable.coordinates
    var creationDate by DragonTable.creationDate
    var age by DragonTable.age
    var wingspan by DragonTable.wingspan
    var color by DragonTable.color
    var type by DragonTable.type
    var killer by Person optionalReferencedOn DragonTable.killer

    override infix operator fun compareTo(other: Dragon): Int =
        { d: Dragon ->  (d.age + d.wingspan).toInt() }.run { this(this@Dragon) - this(other) }

    override infix fun recoverFromJSON(changes: JSONObject): Dragon =
        also {
            listOf(
                { name = changes.getString("name") },
                {
                    coordinates = changes.getJSONObject("coordinates").let {
                        if (it.keySet().contains("coordinatesId")) Coordinates.findById(it.getLong("coordinatesId"))!!
                        else {
                            Coordinates.new {
                                x = it.getDouble("x")
                                y = it.getDouble("y")
                            }
                        }
                    }
                },
                { creationDate = LocalDateTime.now() },
                { age = changes.getInt("age") },
                { wingspan = changes.getDouble("wingspan") },
                { color = Color.valueOf(changes.getString("color")) },
                { type = DragonType.valueOf(changes.getString("type")) },
                { killer = Person.findById(changes.getLong("killer_id")) }
            ).forEach { runCatching(it) }
        }

    override fun transformToJSON(): JSONObject =
        JSONObject().apply {
            put("id", id)
            put("name", name)
            put("coordinates", coordinates.transformToJSON())
            put("creationDate", creationDate)
            put("age", age)
            put("wingspan", wingspan)
            put("color", color.toString())
            put("type", type.toString())
            killer?.let { put("killer", it.transformToJSON()) }
        }

    enum class DragonType { WATER, AIR, FIRE, UNKNOWN }
}
