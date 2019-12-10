package jp.co.musako.application.authentication.model

import jp.co.musako.domain.entity.*
import org.springframework.security.core.*

class AuthenticationUserDetails(
        val user: Users,
        val authorities: List<GrantedAuthority>
) : org.springframework.security.core.userdetails.User(
        user.mail,
        user.password,
        authorities
) {

}