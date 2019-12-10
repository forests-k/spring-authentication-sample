package jp.co.musako.domain.service

import jp.co.musako.domain.entity.*
import jp.co.musako.domain.model.*
import java.util.*

interface UserService {

    fun findById(id: Long): Optional<User>

    fun findAll(): List<User>

    fun create(user: Users): User

    fun update(id: Long, user: Users): User

    fun delete(id: Long)
}