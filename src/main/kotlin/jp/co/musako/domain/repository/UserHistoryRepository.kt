package jp.co.musako.domain.repository

import jp.co.musako.domain.entity.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*

@Repository
interface UserHistoryRepository : JpaRepository<UsersHistory, Long> {
}