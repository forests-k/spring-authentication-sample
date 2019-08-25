package jp.co.sample.domain.repository

import jp.co.sample.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByMail(mail: String): Optional<User>
}