package net.javaguide.springboot.security.DTO;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String old_password;
    private String new_password;
    private String confirm_password;
}
