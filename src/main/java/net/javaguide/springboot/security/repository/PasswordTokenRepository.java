package net.javaguide.springboot.security.repository;


import net.javaguide.springboot.security.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken,Long> {
    Optional<PasswordToken> findByToken(String token);
}
