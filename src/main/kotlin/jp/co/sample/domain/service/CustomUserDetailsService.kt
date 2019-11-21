package jp.co.sample.domain.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails
}