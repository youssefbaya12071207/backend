package net.javaguide.springboot.repository;

import net.javaguide.springboot.model.Employee;
import net.javaguide.springboot.model.Vignette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VignetteRepository extends JpaRepository<Vignette , Long> {

    Optional<Vignette> findByEmail(String email);


}
