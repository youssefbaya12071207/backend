package net.javaguide.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguide.springboot.security.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employees")
public class Employee  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "date_assurance")
    private LocalDate insuranceExpiryDate;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Vignette vignette;
    @JsonIgnore
    @OneToOne( cascade = CascadeType.ALL)
    private User user;


}