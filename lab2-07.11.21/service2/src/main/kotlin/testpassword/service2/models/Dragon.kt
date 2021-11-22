package testpassword.service2.models

import java.util.*
import javax.persistence.*
import kotlin.random.Random

@Entity data class Dragon(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    var name: String,
    @OneToOne(optional = false, cascade = [CascadeType.ALL]) @JoinColumn(name = "coordinates")
    var coordinates: Coordinates = Coordinates(
        null,
        Random.nextDouble(100.0),
        Random.nextDouble(100.0),
        Random.nextDouble(-1000.0, 1000.0)
    ),
    var creationDate: Date = Date(),
    var age: Int,
    var wingspan: Double,
    var color: Color,
    @Enumerated(EnumType.STRING) var type: DragonType,
    @ManyToOne(optional = true, cascade = [CascadeType.ALL]) @JoinColumn(name = "killer_id") var killer: Person?
) {
    enum class DragonType { WATER, AIR, FIRE, UNKNOWN }
}