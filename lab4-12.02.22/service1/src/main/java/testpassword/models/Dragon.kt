package testpassword.models

import org.springframework.http.converter.HttpMessageNotReadableException
import testpassword.dtos.DragonDto
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
    companion object {
        infix fun toDto(d: Dragon) =
            DragonDto().apply {
                id = d.id
                name = d.name
                coordinates = Coordinates toDto d.coordinates
                creationDate = d.creationDate.time
                age = d.age
                wingspan = d.wingspan
                color = d.color.toString()
                type = d.type.toString()
                killerId = d.killer?.id
            }

        infix fun fromDto(d: DragonDto) =
            Dragon(
                null,
                d.name,
                Coordinates fromDto d.coordinates,
                Date(d.creationDate),
                d.age,
                d.wingspan,
                Color.valueOf(d.color),
                DragonType.valueOf(d.type),
                null
            )
    }

    enum class DragonType { WATER, AIR, FIRE, UNKNOWN }

    init {
        if (name.isBlank() || age <= 0 || wingspan <= 0.0) throw HttpMessageNotReadableException("")
    }
}