package testpassword.models

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import testpassword.dtos.PersonDto
import testpassword.repos.DragonsRepo
import javax.persistence.*

@Entity data class Person(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var height: Int,
    @Column(nullable = false) var weight: Int,
    @Column(length = 22, name = "passport_id") var passportId: String,
    @Enumerated(EnumType.STRING) @Column(name = "hair_color") var hairColor: Color,
    @ManyToOne(optional = true, cascade = [CascadeType.ALL], fetch = FetchType.LAZY) @JoinColumn(name = "team_id") var team: Team?
): Comparable<Person> {

    companion object {
        infix fun toDto(p: Person) =
            PersonDto().apply {
                id = p.id
                name = p.name
                height = p.height
                weight = p.weight
                passportId = p.passportId
                hairColor = p.hairColor.toString()
                teamId = p.team?.id
            }

        infix fun fromDto(d: PersonDto) =
            Person(
                null,
                d.name,
                d.height,
                d.weight,
                d.passportId,
                Color.valueOf(d.hairColor),
                null
            )
    }

    @Transient @Autowired private lateinit var dragonsRepo: DragonsRepo

    init {
        if (name.isBlank() || height <= 0 || weight <= 0 || passportId.length !in (5..22)) throw HttpMessageNotReadableException("")
    }

    override infix operator fun compareTo(other: Person): Int =
        { p: Person -> p.weight + p.height + dragonsRepo.findByKiller(other).count() * 2 }.run { this(this@Person) - this(other) }
}