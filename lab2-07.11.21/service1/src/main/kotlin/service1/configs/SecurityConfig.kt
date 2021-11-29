package service1.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration class SecurityConfig {
    @Bean fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf()
            .disable()
            .requiresChannel { it.anyRequest().requiresSecure() }
            .authorizeRequests { it.anyRequest().permitAll() }
            .build()
}

@Configuration class WebConfiguration : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedMethods("*")
    }
}