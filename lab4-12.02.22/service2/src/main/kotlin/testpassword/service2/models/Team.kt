package testpassword.service2.models

import java.io.Serializable
import javax.persistence.*

@Entity data class Team(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    var name: String,
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) var members: MutableList<Person> = emptyList<Person>().toMutableList()
): Serializable