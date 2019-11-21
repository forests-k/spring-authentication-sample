package jp.co.sample.domain.service

import jp.co.sample.application.authentication.model.AuthenticationUserDetails
import jp.co.sample.domain.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("customUserDetailsService")
class CustomUserDetailsServiceImpl(private val userRepository: UserRepository) : CustomUserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByMail(username)
        if (!user.isPresent) throw UsernameNotFoundException("not found user name : $username ")
        return AuthenticationUserDetails(user.get(), arrayListOf(SimpleGrantedAuthority("USER_ROLE")))
    }
}