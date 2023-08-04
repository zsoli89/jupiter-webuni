package hu.webuni.spring.jupiterwebuni.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//      amikor username alapjan kell betolteni usert, userrepository segitsegevel teszi meg
        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        JwtAuthFilter jwtAuthFilter;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf ->
                            csrf.disable()
                    )
                    .sessionManagement(sessionConfig ->
                            sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(auth ->
                            auth
//                                    ezek a matcherek default spring mvc matcherek
                                    .requestMatchers("/api/login/**").permitAll()
//                                    websocket securityt utana kotjuk be
                                    .requestMatchers("/api/stomp/**").permitAll()
//                                    webservicek-re beengedunk mindenkit /services
                                    .requestMatchers("/services").permitAll()
                                    .requestMatchers("/services/**").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/courses/**").hasAuthority("TEACHER")
                                    .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasAuthority("TEACHER")
                                    .anyRequest().permitAll()
//                                    .anyRequest().authenticated()
                    )
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .build()
                    ;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }
    }
