package com.example.backend.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;


    public AppUser create (AppUser appUser) {
        Optional<AppUser> existingAppUser = findByUsername(
                appUser.getUsername()
        );

        if (existingAppUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        appUser.setRole("BASIC");

        appUserRepository.save(appUser);

        appUser.setPassword("");

        return appUser;
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public Optional<AppUser> findByUsernameWithoutPassword(String username) {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        appUser.ifPresent(user -> user.setPassword(""));
        return appUser;
    }

}
