package service1.models

import org.springframework.http.converter.HttpMessageNotReadableException
import java.util.*
import javax.persistence.*
import kotlin.random.Random

@Entity data class Dragon(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    var name: String,
    @OneToOne(optional = false, cascade = [CascadeType.ALL]) @JoinColumn(name = "coordinates")
    var coordinates: Coordinates = Coordinates(
        null,
        Random.nextInt(100).toDouble(),
        Random.nextInt(100).toDouble()
    ),
    var creationDate: Date = Date(),
    var age: Int,
    var wingspan: Double,
    var color: Color,
    @Enumerated(EnumType.STRING) var type: DragonType,
    @ManyToOne(optional = true, cascade = [CascadeType.ALL]) @JoinColumn(name = "killer_id") var killer: Person?
) {
    enum class DragonType { WATER, AIR, FIRE, UNKNOWN }

    init {
        if (name.isBlank() || age <= 0 || wingspan <= 0.0) throw HttpMessageNotReadableException("")
    }
}