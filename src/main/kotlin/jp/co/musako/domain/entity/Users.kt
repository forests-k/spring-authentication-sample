package jp.co.musako.domain.entity

import jp.co.musako.domain.model.*
import java.io.*
import java.time.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class Users(

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

    fun convertToUser() = User(
            id = id,
            mail = mail,
            gender = gender,
            birthdate = birthdate,
            createUserId = createUserId,
            createTimestamp = createTimestamp
    )
}