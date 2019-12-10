package jp.co.sample.domain.model

import java.time.*

class User(
        var id: Long? = null,
        var mail: String? = null,
        var gender: Int? = null,
        var birthdate: LocalDate? = null,
        var createUserId: Long? = null,
        var createTimestamp: LocalDateTime? = null) {
}