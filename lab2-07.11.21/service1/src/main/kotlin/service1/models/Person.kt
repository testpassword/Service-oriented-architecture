package service1.models

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.HttpMessageNotReadableException
import service1.repos.DragonsRepo
import javax.persistence.*

@Entity data class Person(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var height: Int,
    @Column(nullable = false) var weight: Int,
    @Column(length = 22, name = "passport_id") var passportId: String,
    @Enumerated(EnumType.STRING) @Column(name = "hair_color") var hairColor: Color,
    @ManyToOne(optional = true, cascade = [CascadeType.ALL]) @JoinColumn(name = "team_id") var team: Team?
): Comparable<Person> {

    @Transient @Autowired private lateinit var dragonsRepo: DragonsRepo

    init {
        if (name.isBlank() || height <= 0 || weight <= 0 || passportId.length !in (5..22)) throw HttpMessageNotReadableException("")
    }

    override infix operator fun compareTo(other: Person): Int =
        { p: Person -> p.weight + p.height + dragonsRepo.findByKiller(other).count() * 2 }.run { this(this@Person) - this(other) }
}