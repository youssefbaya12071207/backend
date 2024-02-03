package net.javaguide.springboot.security.utils;


import lombok.AllArgsConstructor;
import net.javaguide.springboot.security.model.Role;
import net.javaguide.springboot.security.model.User;
import net.javaguide.springboot.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Init implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        try{
            User user = new User();
            user.setEmail("admin@app.com");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setAdmin(true);
            user.setRole(Role.ADMIN);
            user.setEnabled(true);
            userRepository.save(user);
        }catch (Exception e){
            System.out.println("error : "+e.getMessage());
        }
    }
}
