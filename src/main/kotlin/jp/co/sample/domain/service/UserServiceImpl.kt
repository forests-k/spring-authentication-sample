package jp.co.sample.domain.service

import jp.co.sample.domain.model.User
import jp.co.sample.domain.model.UserHistory
import jp.co.sample.domain.repository.UserHistoryRepository
import jp.co.sample.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional


@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val userHistoryRepository: UserHistoryRepository,
        private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun findById(id: Long): Optional<User> {
        val user = userRepository.findById(id)
        // passwordを隠蔽
        user.let { user -> user.get().password = "" }
        return user
    }

    override fun findAll(): List<User> {
        val userList = userRepository.findAll()
        userList.forEach { user -> user.password = "" }
        return userList
    }

    @Transactional
    override fun create(user: User): User {
        val now = LocalDateTime.now()
        user.createTimestamp = now

        user.password = passwordEncoder.encode(user.password)

        val currentUser = userRepository.save(user)

        // 変更履歴を登録
        val userHistory = UserHistory()
        userHistory.userId = currentUser.id
        userHistory.mail = currentUser.mail
        userHistory.gender = currentUser.gender
        userHistory.birthdate = currentUser.birthdate
        userHistory.password = currentUser.password
        userHistory.createTimestamp = now
        userHistory.note = "create user"
        userHistoryRepository.save(userHistory)

        return currentUser
    }

    @Transactional
    override fun update(id: Long, user: User): User {
        val currentUser = userRepository.findById(id).orElseThrow { IllegalArgumentException() }

        val now = LocalDateTime.now()

        // 変更内容を登録
        currentUser.mail = currentUser.mail
        currentUser.gender = currentUser.gender
        currentUser.birthdate = currentUser.birthdate
        currentUser.createTimestamp = now
        userRepository.save(currentUser)

        // 変更履歴を登録
        val userHistory = UserHistory()
        userHistory.userId = currentUser.id
        userHistory.mail = currentUser.mail
        userHistory.gender = currentUser.gender
        userHistory.birthdate = currentUser.birthdate
        userHistory.password = currentUser.password
        userHistory.createTimestamp = now
        userHistory.note = "edit user"
        userHistoryRepository.save(userHistory)

        return currentUser
    }

    @Transactional
    override fun delete(id: Long) {
        val currentUser = userRepository.findById(id).orElseThrow { IllegalArgumentException() }

        val now = LocalDateTime.now()

        // 変更履歴を登録
        val userHistory = UserHistory()
        userHistory.userId = currentUser.id
        userHistory.mail = currentUser.mail
        userHistory.gender = currentUser.gender
        userHistory.birthdate = currentUser.birthdate
        userHistory.password = currentUser.password
        userHistory.createTimestamp = now
        userHistory.note = "delete user"
        userHistoryRepository.save(userHistory)

        userRepository.deleteById(id)
    }
}