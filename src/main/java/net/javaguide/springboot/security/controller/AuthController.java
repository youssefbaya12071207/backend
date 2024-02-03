package net.javaguide.springboot.security.controller;


import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.javaguide.springboot.security.DTO.LoginRequest;
import net.javaguide.springboot.security.DTO.LoginResponse;
import net.javaguide.springboot.security.DTO.ResetPasswordDto;
import net.javaguide.springboot.security.DTO.UpdatePasswordDTO;
import net.javaguide.springboot.security.config.WebSecurityConstant;
import net.javaguide.springboot.security.service.UserService;
import net.javaguide.springboot.security.utils.JwtUtils;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        String token = null;
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword())
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtUtils.generateJwtToken(loginRequest.getEmail());
            Cookie cookie = new Cookie(WebSecurityConstant.JWT_COOKIE_NAME, token);
            cookie.setPath("/");
            // cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) WebSecurityConstant.EXPIRATION_TIME);
            // -------------------- set Cookie into request -----------
            response.addCookie(cookie);
            response.setStatus(HttpStatus.OK.value());
            response.addHeader(WebSecurityConstant.JWT_COOKIE_NAME, WebSecurityConstant.TOKEN_PREFIX + token);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return new ResponseEntity(LoginResponse.builder().token(token).build(), HttpStatusCode.valueOf(200));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request){
        ResponseCookie cookie = ResponseCookie.from(WebSecurityConstant.JWT_COOKIE_NAME, null).path("/").build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.SET_COOKIE, cookie.toString());
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/check-authentication")
    public ResponseEntity<?> checkAuthentication(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, WebSecurityConstant.JWT_COOKIE_NAME);
        String cookieValue = cookie.getValue();
        if ( cookieValue == null || cookieValue.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }

    @PermitAll
    @GetMapping("/reset-password/{email}")
    public ResponseEntity<?> token_reset_password(@PathVariable String email) {
        userService.createTokenResetAccount(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PermitAll
    @PostMapping("/reset-password")
    public ResponseEntity<?> reset_password(@RequestBody ResetPasswordDto body) {
        userService.resetPassword(body.getToken(), body.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-pwd")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO passwordDTO) {
        userService.updatePassword(passwordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
