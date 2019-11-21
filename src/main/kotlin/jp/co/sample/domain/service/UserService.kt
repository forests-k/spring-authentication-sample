package jp.co.sample.domain.service

import jp.co.sample.domain.model.User
import java.util.*

interface UserService {

    fun findById(id: Long): Optional<User>

    fun findAll(): List<User>

    fun create(user: User): User

    fun update(id: Long, user: User): User

    fun delete(id: Long)
}