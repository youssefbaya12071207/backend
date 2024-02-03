package net.javaguide.springboot.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginDto {
    private String emailId;
    private String password;
}
