package jp.co.musako.domain.entity

import com.fasterxml.jackson.annotation.*
import java.io.*
import java.time.*
import javax.persistence.*

@Entity
@Table(name = "users_history")
data class UsersHistory(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(name = "user_id")
        var userId: Long? = null,

        @Column(name = "mail")
        var mail: String? = null,

        @Column(name = "gender")
        var gender: Int? = null,

        @Column(name = "birthdate")
        var birthdate: LocalDate? = null,

        @Column(name = "password")
        @JsonIgnore
        var password: String? = null,

        @Column(name = "note")
        var note: String? = null,

        @Column(name = "create_user_id")
        var createUserId: Long? = null,

        @Column(name = "create_timestamp")
        var createTimestamp: LocalDateTime? = null) : Serializable {
}