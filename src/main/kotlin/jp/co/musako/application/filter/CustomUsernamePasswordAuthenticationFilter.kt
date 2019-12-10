package jp.co.musako.application.filter

import com.fasterxml.jackson.databind.exc.*
import com.fasterxml.jackson.module.kotlin.*
import jp.co.musako.application.authentication.model.*
import org.slf4j.*
import org.springframework.http.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.web.authentication.*
import java.io.*
import javax.servlet.http.*

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

        } catch (e: MismatchedInputException) {
            log.warn("request parameter is valid")
            authRequest = UsernamePasswordAuthenticationToken("anymouse", "dummy")
            setDetails(request, UsernamePasswordAuthenticationToken("anymouse", ""))
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