package testpassword.repos

import org.jetbrains.exposed.dao.id.LongIdTable

object CoordinatesTable: LongIdTable("coordinates") {

    val x = double("x")
    val y = double("y")
}