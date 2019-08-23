package jp.co.sample.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
//import javax.persistence.*

//@Entity
//@Table(name = "user")
data class User(

        //@Id
        //@GeneratedValue
        val id: Int,

        //@Column(name = "mail")
        val mail: String,

        //@Column(name = "password")
        val password: String,

        //@Column(name = "gender")
        val gender: Int,

        //@Column(name = "birthdate")
        val birthdate: LocalDate,

        //@Column(name = "create_user_id")
        val createUserId: Int,

        //@Column(name = "create_timestamp")
        val createTimestamp: LocalDateTime,

        //@Column(name = "update_user_id")
        val updateUserId: Int,

        //@Column(name = "update_timestamp")
        val updateTimestamp: LocalDateTime

): Serializable {
}