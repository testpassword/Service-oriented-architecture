package testpassword.service2.models

import javax.persistence.*

@Entity data class Person(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var height: Int,
    @Column(nullable = false) var weight: Int,
    @Column(length = 22, name = "passport_id") var passportId: String,
    @Enumerated(EnumType.STRING) @Column(name = "hair_color") var hairColor: Color,
    @ManyToOne(optional = true, cascade = [CascadeType.ALL]) @JoinColumn(name = "team_id") var team: Team?
)