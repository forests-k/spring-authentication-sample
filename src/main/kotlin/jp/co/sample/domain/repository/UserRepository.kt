package jp.co.sample.domain.repository

import jp.co.sample.domain.entity.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import java.util.*

@Repository
interface UserRepository : JpaRepository<Users, Long> {

    fun findByMail(mail: String): Optional<Users>
}