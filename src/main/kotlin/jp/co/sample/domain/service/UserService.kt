package jp.co.sample.domain.service

import jp.co.sample.domain.entity.*
import jp.co.sample.domain.model.*
import java.util.*

interface UserService {

    fun findById(id: Long): Optional<User>

    fun findAll(): List<User>

    fun create(user: Users): User

    fun update(id: Long, user: Users): User

    fun delete(id: Long)
}