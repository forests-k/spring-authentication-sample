package jp.co.sample.application.authentication.model

import jp.co.sample.domain.model.User
import org.springframework.security.core.GrantedAuthority

class AuthenticationUserDetails(
        val user: User,
        val authorities: List<GrantedAuthority>
) : org.springframework.security.core.userdetails.User(
        user.mail,
        user.password,
        authorities
) {

}