package com.example.backend.appUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    private String id;
    private String username;
    private String password;

}
