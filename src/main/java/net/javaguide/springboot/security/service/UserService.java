package net.javaguide.springboot.security.service;


import net.javaguide.springboot.security.DTO.UpdatePasswordDTO;
import net.javaguide.springboot.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void updatePassword(UpdatePasswordDTO passwordDTO);
    User getCurrentUser();
    void deleteUser(long id);
    User create(User account);
    void resetPassword(String token, String password);
    void createTokenResetAccount(String email);


    User update(User user);


}
