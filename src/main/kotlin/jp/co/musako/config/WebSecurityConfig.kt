package jp.co.musako.config

import jp.co.musako.application.authentication.*
import jp.co.musako.application.filter.*
import jp.co.musako.domain.service.*
import org.slf4j.*
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.crypto.password.*
import org.springframework.security.web.authentication.*
import org.springframework.security.web.savedrequest.*
import org.springframework.web.cors.*

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
        private val customUserDetailService: CustomUserDetailsService,
        private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
        private val customAccessDeniedHandler: CustomAccessDeniedHandler,
        private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
        private val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler,
        private val passwordEncoder: PasswordEncoder
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
        //http
        //.sessionManagement()
        //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

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
        corsConfiguration.allowedMethods = listOf(CorsConfiguration.ALL)
        corsConfiguration.allowedOrigins = listOf(CorsConfiguration.ALL)
        corsConfiguration.allowedHeaders = listOf(CorsConfiguration.ALL)
        corsConfiguration.allowCredentials = true
        corsConfiguration.maxAge = 1800L

        val corsSource = UrlBasedCorsConfigurationSource()
        corsSource.registerCorsConfiguration("/*", corsConfiguration)

        return corsSource
    }
}