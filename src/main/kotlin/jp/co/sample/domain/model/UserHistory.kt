package jp.co.sample.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user_history")
data class UserHistory(

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