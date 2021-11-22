package service1.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import kotlin.math.pow

@Entity data class Coordinates(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    var x: Double,
    var y: Double
): Comparable<Coordinates> {

    override infix operator fun compareTo(other: Coordinates): Int {
        val hypotenuse: (Coordinates) -> Double = { x.pow(2) + y.pow(2) }
        return (hypotenuse(this) - hypotenuse(other)).toInt()
    }
}