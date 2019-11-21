package jp.co.sample.application.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.co.sample.application.authentication.model.Credential
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.BufferedReader
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomUsernamePasswordAuthenticationFilter : UsernamePasswordAuthenticationFilter() {

    companion object {
        private val log = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        if (!HttpMethod.POST.name.equals(request.method)) {
            throw AuthenticationServiceException("Authentication method not supported: " + request.method)
        }

        var authRequest: UsernamePasswordAuthenticationToken?
        try {
            val sb = StringBuffer()
            val reader: BufferedReader = request.reader

            do {
                val line = reader.readLine()
                line ?: break
                sb.append(line)
            } while (line != null)

            val credential = jacksonObjectMapper().readValue<Credential>(sb.toString())

            authRequest = UsernamePasswordAuthenticationToken(credential.username, credential.password)

            setDetails(request, authRequest)
        } catch (e: Exception) {
            log.error("authentication error", e)
            authRequest = UsernamePasswordAuthenticationToken("anymouse", "dummy")
            setDetails(request, UsernamePasswordAuthenticationToken("anymouse", ""))
        }

        try {
            return authenticationManager.authenticate(authRequest)
        } catch (e: IllegalArgumentException) {
            throw AuthenticationServiceException("Authentication password is not HEX")
        }
    }
}