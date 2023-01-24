package com.example.backend.appUser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appuser")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;


}
