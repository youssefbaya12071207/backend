package net.javaguide.springboot.security.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
