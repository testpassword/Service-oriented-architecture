package testpassword.service2.models

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

    override infix operator fun compareTo(other: Coordinates): Int {
        val hypotenuse: (Coordinates) -> Double = { x.pow(2) + y.pow(2) }
        val h = hypotenuse(this)
        val hO = hypotenuse(other)
        return (if (h != hO) h - hO else this.z - other.z).toInt()
    }
}