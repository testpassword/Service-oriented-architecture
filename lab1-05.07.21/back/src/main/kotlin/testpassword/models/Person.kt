package testpassword.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.json.JSONObject
import testpassword.repos.*

class Person(id: EntityID<Long>): LongEntity(id), Comparable<Person>, JSONRecoverable<Person> {

    companion object: LongEntityClass<Person>(PersonTable)
    var name by PersonTable.name
    var height by PersonTable.height
    var weight by PersonTable.weight
    var passportID by PersonTable.passportID
    var hairColor by PersonTable.hairColor

    override infix operator fun compareTo(other: Person): Int =
        { p: Person ->
            p.weight + p.height + Dragon
                    .find { DragonTable.killer eq other.id }
                    .count()
                    .toInt() * 2
        }.run { this(this@Person) - this(other) }

    override infix fun recoverFromJSON(changes: JSONObject): Person =
            also {
                listOf(
                    { name = changes.getString("name") },
                    { height = changes.getInt("height") },
                    { weight = changes.getInt("weight") },
                    { passportID = changes.getString("passportID") },
                    { hairColor = Color.valueOf(changes.getString("hairColor")) }
                ).forEach { runCatching(it) }
            }
}