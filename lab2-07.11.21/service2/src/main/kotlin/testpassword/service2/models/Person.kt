package testpassword.service2.models

import javax.persistence.*

@Entity data class Person(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int?,
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var height: Int,
    @Column(nullable = false) var weight: Int,
    @Column(length = 22) var passportId: String,
    @Enumerated(EnumType.STRING) var hairColor: Color
)