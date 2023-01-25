package com.example.backend.appUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.Mockito.*;

class AppUserServiceTest {


    @Test
    void findByUsername_returnsUsernameWhenFound() {
        //Given
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        when(appUserRepository.findByUsername("Test User")).thenReturn(
                Optional.of(new AppUser("Test ID", "Test User", "Test Password", "BASIC")));
        //When
        AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder);
        Optional<AppUser> actual = appUserService.findByUsername("Test User");

        //Then
        Assertions.assertEquals(
                Optional.of(
                        new AppUser("Test ID", "Test User", "Test Password", "BASIC")
                ), actual);
        verify(appUserRepository).findByUsername("Test User");
    }

    @Test
    void findByUsername_returnsNullWhenNotFound() {
        //Given
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        when(appUserRepository.findByUsername("Test User")).thenReturn(
                Optional.ofNullable(null));
        //When
        AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder);
        Optional<AppUser> actual = appUserService.findByUsername("Test User");

        //Then
        Assertions.assertEquals(Optional.empty(), actual);
        verify(appUserRepository).findByUsername("Test User");
    }

    @Test
    void findByUsernameWithoutPassword_returnsUserIfRegistered() {
        //Given
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        when(appUserRepository.findByUsername("Test User")).thenReturn(
                Optional.of(
                        new AppUser(
                                "Test ID",
                                "Test User",
                                "Test Password",
                                "BASIC")
                ));
        //When
        AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder);
        Optional<AppUser> actual = appUserService.findByUsernameWithoutPassword("Test User");
        //Then
        Assertions.assertEquals(
                Optional.of(
                        new AppUser(
                                "Test ID",
                                "Test User",
                                "",
                                "BASIC"))
                , actual);
        verify(appUserRepository).findByUsername("Test User");

    }

    @Test
    void findByUsernameWithoutPassword_returnsNullIfUserNotRegistered() {
        //Given
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        when(appUserRepository.findByUsername("Test User")).thenReturn(
                Optional.ofNullable(null));
        //When
        AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder);
        Optional<AppUser> actual = appUserService.findByUsernameWithoutPassword("Test User");
        //Then
        Assertions.assertEquals(
                Optional.empty(), actual);
        verify(appUserRepository).findByUsername("Test User");
    }

    @Test
    void create_returnsUserIfCorrectlyCreated() {
        //Given
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        when(appUserRepository.findByUsername("Test User")).thenReturn(Optional.empty());

        when(appUserRepository.save(
                new AppUser(
                        "Test ID",
                        "Test User",
                        passwordEncoder.encode("Test Password"),
                        "BASIC")))
                .thenReturn(
                new AppUser(
                        "Test ID",
                        "Test User",
                        "",
                        "BASIC"));
        //When
        AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder);
        AppUser actual = appUserService.create(
                new AppUser(
                        "Test ID",
                        "Test User",
                        "Test Password",
                        "BASIC"));

        //Then
        Assertions.assertEquals(
                new AppUser(
                        "Test ID",
                        "Test User",
                        "",
                        "BASIC")
                , actual);
        verify(appUserRepository).save(
                new AppUser(
                        "Test ID",
                        "Test User",
                        "",
                        "BASIC")
        );

    }

    @Test
    void create_throwsResponseStatusExceptionWhenUserAlreadyRegistered() {
        //Given
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

        when(appUserRepository.findByUsername("Test User")).thenReturn(
                Optional.of(
                    new AppUser(
                            "Test ID",
                            "Test User",
                            "Test Password",
                            "BASIC")
                ));
        //When
        AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> appUserService.create(
                        new AppUser(
                            "Test ID",
                            "Test User",
                            "Test Password",
                            "BASIC")));

    }

}