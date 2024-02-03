package net.javaguide.springboot.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguide.springboot.security.model.Role;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate insuranceExpiryDate;
    private String password;
    private Role role;
}
