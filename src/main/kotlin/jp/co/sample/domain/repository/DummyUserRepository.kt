package jp.co.sample.domain.repository

import jp.co.sample.domain.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class DummyUserRepository(
        @Autowired val passwordEncoder: PasswordEncoder) {

    fun findByMail(mail: String): Optional<User> {
        return Optional.of(User(1,"kzzuki.mori@ziyo.jp",passwordEncoder.encode("passw0rd"), 1,LocalDate.now(),0,LocalDateTime.now(),0, LocalDateTime.now()))
    }
}