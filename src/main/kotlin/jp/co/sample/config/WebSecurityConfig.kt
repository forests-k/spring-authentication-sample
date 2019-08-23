package jp.co.sample.config

import jp.co.sample.application.authentication.CustomAccessDeniedHandler
import jp.co.sample.application.authentication.CustomAuthenticationEntryPoint
import jp.co.sample.application.authentication.CustomAuthenticationFailureHandler
import jp.co.sample.application.authentication.CustomAuthenticationSuccessHandler
import jp.co.sample.application.filter.CustomUsernamePasswordAuthenticationFilter
import jp.co.sample.domain.service.CustomUserDetailsService
import org.apache.catalina.util.RequestUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.support.RequestContextUtils

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
        @Autowired val customUserDetailService: CustomUserDetailsService,
        @Autowired val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
        @Autowired val customAccessDeniedHandler: CustomAccessDeniedHandler,
        @Autowired val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
        @Autowired val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler,
        @Autowired val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    companion object {
        private val log = LoggerFactory.getLogger(WebSecurityConfig::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
                .eraseCredentials(true)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("^/actuator/*")
    }

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .mvcMatchers("*/actuator/*", "*/login")
                .permitAll()
                .anyRequest()
                .authenticated()
        http
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .accessDeniedPage("/access-denied")

        http
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .successForwardUrl("/authentication")
                .permitAll()

        http
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()


        http.csrf().disable()

        http.cors().configurationSource(this.customCorsConfigurationSource())

        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        // リクエストごとに認証を行うようセッションポリシーをステートレスにする
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.requestCache().requestCache(NullRequestCache())
    }

    @Throws(Exception::class)
    private fun authenticationFilter(): CustomUsernamePasswordAuthenticationFilter {
        val filter = CustomUsernamePasswordAuthenticationFilter()
        filter.setFilterProcessesUrl("/login")
        filter.setAuthenticationManager(authenticationManagerBean())
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler)
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)
        return filter
    }

    private fun customCorsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedMethod("*")
        corsConfiguration.addAllowedOrigin("*")
        corsConfiguration.addAllowedHeader("X-AUTH-KEY")
        corsConfiguration.setMaxAge(1800L)

        val corsSource = UrlBasedCorsConfigurationSource()
        corsSource.registerCorsConfiguration("/*", corsConfiguration)

        return corsSource
    }
}