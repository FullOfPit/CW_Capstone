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
                .authorizeHttpRequests()
                //ToDo Take THIS POST OUT AFTER PROPER TESTING
                .antMatchers(HttpMethod.POST, "/api/appuser").permitAll()
                .antMatchers(HttpMethod.POST, "/api/appuser/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/appuser/me").authenticated()
                .antMatchers(HttpMethod.GET, "/api/risk").authenticated()
                .antMatchers(HttpMethod.POST, "/api/risk").authenticated()
                .anyRequest()
                .authenticated()
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
