package jp.co.sample.domain.repository

import jp.co.sample.domain.model.UserHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserHistoryRepository : JpaRepository<UserHistory, Long> {
}