package net.javaguide.springboot.security.DTO;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String token;
    private String password;
}
