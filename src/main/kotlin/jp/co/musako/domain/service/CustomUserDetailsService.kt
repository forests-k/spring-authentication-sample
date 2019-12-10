package jp.co.musako.domain.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface CustomUserDetailsService : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails
}