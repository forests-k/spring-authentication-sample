package jp.co.musako.application.authentication

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomAuthenticationSuccessHandler : AuthenticationSuccessHandler {

    companion object {
        private val log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler::class.java)
    }

    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {

        response?.isCommitted() ?: return
        response.status = HttpStatus.OK.value()
        clearAuthenticationAttributes(request)
    }

    private fun clearAuthenticationAttributes(request: HttpServletRequest?) {
        val session = request?.getSession(false) ?: return

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)
    }
}