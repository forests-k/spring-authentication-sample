package jp.co.sample.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(name = "mail")
        var mail: String? = null,

        @Column(name = "password")
        var password: String? = null,

        @Column(name = "gender")
        var gender: Int? = null,

        @Column(name = "birthdate")
        var birthdate: LocalDate? = null,

        @Column(name = "create_user_id")
        var createUserId: Long? = null,

        @Column(name = "create_timestamp")
        var createTimestamp: LocalDateTime? = null

) : Serializable {

}