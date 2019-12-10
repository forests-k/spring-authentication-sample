package jp.co.sample.domain.repository

import jp.co.sample.domain.entity.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*

@Repository
interface UserHistoryRepository : JpaRepository<UsersHistory, Long> {
}