package net.javaguide.springboot.security.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;


public enum Role {
    ADMIN ,
    USER;
}
