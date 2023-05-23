package com.example.backend.config;

import com.example.backend.appuser.AppUser;
import com.example.backend.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppUserService appUserService;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .httpBasic().and()
                .httpBasic().authenticationEntryPoint(new AuthenticationPopUpBlock()).and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/api/app-users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/app-users/login").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        return username -> {
            Optional<AppUser> user = appUserService.findByUsername(username);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException(username);
            }

            AppUser appUser = user.get();

            return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .roles(appUser.getRole())
                    .build();
        };
    }
}
