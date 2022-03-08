package testpassword.models

import testpassword.dtos.CoordinateDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import kotlin.math.pow

@Entity data class Coordinates(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    var x: Double,
    var y: Double,
    var z: Double
): Comparable<Coordinates> {

    companion object {
        infix fun toDto(c: Coordinates) =
            CoordinateDto().apply {
                id = c.id
                x = c.x
                y = c.y
                z = c.z
            }

        infix fun fromDto(d: CoordinateDto) =
            Coordinates(null, d.x, d.y, d.z)
    }

    override infix operator fun compareTo(other: Coordinates): Int {
        val hypotenuse: (Coordinates) -> Double = { x.pow(2) + y.pow(2) }
        return (hypotenuse(this) - hypotenuse(other)).let { if (it != 0.0) it else this.z - other.z }.toInt()
    }
}