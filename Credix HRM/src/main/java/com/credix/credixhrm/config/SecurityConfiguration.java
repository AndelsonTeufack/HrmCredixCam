package com.credix.credixhrm.config;


import com.credix.credixhrm.exceptions.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration{
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

        http

                .cors().and()
                .csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers("/auth/login", "/auth/signup","/auth/forgotPassword").permitAll()

                .requestMatchers(HttpMethod.GET, "/announcements/manage/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/announcements/manage/**").hasAnyRole("ADMIN","SUPERADMIN")
                .requestMatchers(HttpMethod.PUT, "/announcements/manage/**").hasAnyRole("ADMIN","SUPERADMIN")
                .requestMatchers(HttpMethod.DELETE, "/announcements/manage/**").hasAnyRole("ADMIN","SUPERADMIN")

                .requestMatchers(HttpMethod.GET, "/employees/manage/**").hasAnyRole("SUPERADMIN", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/employees/manage/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.PUT, "/employees/manage/**").hasRole("SUPERADMIN")
                .requestMatchers(HttpMethod.DELETE, "/employees/manage/**").hasRole("SUPERADMIN")

                .requestMatchers(HttpMethod.GET, "/holidays/manage/**").hasAnyRole("SUPERADMIN", "ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/holidays/manage/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/holidays/manage/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/holidays/manage/**").hasAnyRole("SUPERADMIN", "ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}

