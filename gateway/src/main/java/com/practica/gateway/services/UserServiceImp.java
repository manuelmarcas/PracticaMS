package com.practica.gateway.services;

import com.practica.gateway.domain.User;
import com.practica.gateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImp implements IUserService {

    @Autowired
    UserRepository userRepository;

    public Mono<User> save(User user){
        return userRepository.save(user);
    }

}
