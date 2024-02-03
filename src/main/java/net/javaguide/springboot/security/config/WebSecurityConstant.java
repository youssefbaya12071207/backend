package net.javaguide.springboot.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebSecurityConstant {

    public static long EXPIRATION_TIME;
    public static String TOKEN_SECRET;
    public static String JWT_COOKIE_NAME = "Authorization";
    public static String TOKEN_PREFIX;

    @Value("${app.jwt_cookie_name}")
    public void setCookieName(String name) {
        WebSecurityConstant.JWT_COOKIE_NAME = name;
    }

    @Value("${app.token_secret}")
    public void setTokenSecret(String name) {
        WebSecurityConstant.TOKEN_SECRET = name;
    }

    @Value("${app.token_prefix}")
    public void setTokenPrefix(String name) {
        WebSecurityConstant.TOKEN_PREFIX = name;
    }

    @Value("${app.expiration_time}")
    public void setExpirationTime(long time) {
        WebSecurityConstant.EXPIRATION_TIME = time;
    }
}

