package me.jazzy.obp.security.config;

import lombok.AllArgsConstructor;
import me.jazzy.obp.security.jwt.JwtAuthFilter;
import me.jazzy.obp.security.jwt.JwtGenerator;
import me.jazzy.obp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@EnableWebMvc
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtGenerator jwtGenerator;

    private static final String[] WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authenticationProvider(provider())
                .authorizeHttpRequests(configurer -> {
                    configurer.requestMatchers(WHITE_LIST)
                            .permitAll();
                    configurer.requestMatchers(HttpMethod.POST, "/api/v*/auth/**")
                            .permitAll();
                    configurer.requestMatchers("/api/v*/mails/**")
                            .permitAll();
                    configurer.requestMatchers(HttpMethod.GET, "api/v*/categories")
                            .permitAll();
                    configurer.requestMatchers(HttpMethod.POST, "/api/v*/categories")
                            .hasAuthority("ADMIN");
                    configurer.requestMatchers(HttpMethod.PUT, "/api/v*/categories")
                            .hasAuthority("ADMIN");
                    configurer.requestMatchers("/api/v*/comments/**")
                            .permitAll();
                    configurer.requestMatchers(HttpMethod.GET, "/api/v*/posts")
                            .permitAll();
                    configurer.requestMatchers("/api/v*/posts/**")
                            .hasAnyAuthority("ADMIN", "BLOGGER");
                    configurer.requestMatchers("/api/v1/users/**")
                            .hasAuthority("ADMIN");
                    configurer.anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthFilter authFilter() {
        return new JwtAuthFilter(jwtGenerator, userService);
    }

}