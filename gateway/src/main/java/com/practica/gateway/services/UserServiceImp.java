package com.practica.gateway.services;

import com.practica.gateway.domain.Rol;
import com.practica.gateway.domain.User;
import com.practica.gateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImp implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    public User save(String username, String pass, String rol){

        User nuevo = new User();
        nuevo.setUsername(username);
        nuevo.setPassword(passwordEncoder().encode(pass));


        Set<SimpleGrantedAuthority> roles = new HashSet<SimpleGrantedAuthority>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(rol);
        roles.add(role);

        nuevo.setRoles(roles);

        return userRepository.save(nuevo).block();
    }

}
