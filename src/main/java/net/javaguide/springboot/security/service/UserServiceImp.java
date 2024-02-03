package net.javaguide.springboot.security.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.javaguide.springboot.security.DTO.UpdatePasswordDTO;
import net.javaguide.springboot.security.model.User;
import net.javaguide.springboot.security.repository.PasswordTokenRepository;
import net.javaguide.springboot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.javaguide.springboot.security.model.*;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void updatePassword(UpdatePasswordDTO passwordDTO) {
        User user = this.getCurrentUser();
        String encodedPwd = passwordEncoder.encode(passwordDTO.getOld_password());
        if(!encodedPwd.equals(user.getPassword())
                || !passwordDTO.getNew_password().equals(passwordDTO.getConfirm_password())){
            throw new RuntimeException("password incorrect");
        }
        user.setPassword(passwordEncoder.encode(passwordDTO.getNew_password()));
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return this.findByEmail(auth.getName());
    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public User create(User account) {
        Optional<User> user = userRepository.findByEmail(account.getEmail());
        if(user.isPresent()){
            throw new RuntimeException("email already taken");
        }
        account.setPassword(
                passwordEncoder.encode(account.getPassword())
        );
        return userRepository.save(account);
    }

    @Override
    public void resetPassword(String token, String password) {
        PasswordToken passwordToken = passwordTokenRepository
                .findByToken(token).orElseThrow(()->new RuntimeException("token is expired"));
        if(passwordToken.isExpired()){
            throw new RuntimeException("token expired");
        }
        passwordToken.getUser().setPassword(
                passwordEncoder.encode(password)
        );
        userRepository.save(passwordToken.getUser());
        passwordToken.setExpired(true);
        passwordTokenRepository.save(passwordToken);
    }

    @Override
    public void createTokenResetAccount(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        String token = UUID.randomUUID().toString();
        PasswordToken passwordToken = new PasswordToken();
        passwordToken.setToken(token);
        passwordToken.setUser(user);
        passwordTokenRepository.save(passwordToken);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
    private User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
    }
}
