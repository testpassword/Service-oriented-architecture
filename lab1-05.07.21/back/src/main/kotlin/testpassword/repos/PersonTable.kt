package testpassword.repos

import org.jetbrains.exposed.dao.id.LongIdTable
import testpassword.extensions.*
import testpassword.models.Color
import testpassword.models.Person

object PersonTable: LongIdTable("person") {
    val name = text("name").check(op = isNotBlank)
    val height = integer("height").check(op = isPositiveInt)
    val weight = integer("weight").check(op = isPositiveInt)
    val passportID = varchar("passport_id", 22).check(op = isNotBlank)
    val hairColor = enumeration("hairColor", Color::class)

    infix fun `find person included in name`(pattern: String): Set<Person> = Person.find { name like "%${pattern}%" }.toSet()
}