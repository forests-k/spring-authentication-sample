package jp.co.sample.application.filter

import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggingFilter : OncePerRequestFilter() {

    companion object {
        private val log = LoggerFactory.getLogger(LoggingFilter::class.java)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val start = System.currentTimeMillis()
        try {
            log.info("[BEGIN] {} {}", request.method, request.requestURI)
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            log.error(e.localizedMessage)
            throw e
        } finally {
            log.info("[END] {} {} {}ms status:{}", request.method, request.requestURI, (System.currentTimeMillis() - start), response.status)
        }

    }
}