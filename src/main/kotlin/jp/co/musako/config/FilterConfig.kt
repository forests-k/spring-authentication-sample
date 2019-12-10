package jp.co.musako.config

import jp.co.musako.application.filter.LoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter

@Configuration
class FilterConfig {

    @Bean
    fun loggingFilter(): FilterRegistrationBean<out Filter> {
        val bean = FilterRegistrationBean(LoggingFilter())
        bean.addUrlPatterns("*")
        return bean
    }
}