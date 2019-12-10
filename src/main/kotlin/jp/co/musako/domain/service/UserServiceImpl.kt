package jp.co.musako.domain.service

import jp.co.musako.domain.entity.*
import jp.co.musako.domain.model.*
import jp.co.musako.domain.repository.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*
import java.time.*
import java.util.*
import javax.transaction.*


@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val userHistoryRepository: UserHistoryRepository,
        private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun findById(id: Long): Optional<User> {
        val users = userRepository.findById(id)
        return users.map { users -> users.convertToUser() }
    }

    override fun findAll(): List<User> {
        val userList = userRepository.findAll()
        // passwordを隠蔽
        userList.forEach { user -> user.password = "" }
        return userList.map { users -> users.convertToUser() }
    }

    @Transactional
    override fun create(user: Users): User {
        val now = LocalDateTime.now()
        user.createTimestamp = now

        user.password = passwordEncoder.encode(user.password)

        val currentUser = userRepository.save(user)

        // 変更履歴を登録
        userHistoryRepository.save(
                UsersHistory(
                        userId = currentUser.id,
                        mail = currentUser.mail,
                        gender = currentUser.gender,
                        birthdate = currentUser.birthdate,
                        password = currentUser.password,
                        createTimestamp = now,
                        note = "create user")
        )

        return currentUser.convertToUser()
    }

    @Transactional
    override fun update(id: Long, user: Users): User {
        val currentUser = userRepository.findById(id).orElseThrow { IllegalArgumentException() }

        val now = LocalDateTime.now()

        // 変更内容を登録
        currentUser.mail = user.mail
        currentUser.gender = user.gender
        currentUser.birthdate = user.birthdate
        currentUser.createTimestamp = now
        userRepository.save(currentUser)

        // 変更履歴を登録
        userHistoryRepository.save(
                UsersHistory(
                        userId = currentUser.id,
                        mail = currentUser.mail,
                        gender = currentUser.gender,
                        birthdate = currentUser.birthdate,
                        password = currentUser.password,
                        createTimestamp = now,
                        note = "edit user")
        )

        return currentUser.convertToUser()
    }

    @Transactional
    override fun delete(id: Long) {
        val currentUser = userRepository.findById(id).orElseThrow { IllegalArgumentException() }

        val now = LocalDateTime.now()

        // 変更履歴を登録
        userHistoryRepository.save(
                UsersHistory(
                        userId = currentUser.id,
                        mail = currentUser.mail,
                        gender = currentUser.gender,
                        birthdate = currentUser.birthdate,
                        createTimestamp = now,
                        note = "delete user")
        )

        userRepository.deleteById(id)
    }
}