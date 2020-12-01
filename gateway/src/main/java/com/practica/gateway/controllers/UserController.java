package com.practica.gateway.controllers;

import com.practica.gateway.domain.User;
import com.practica.gateway.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("crear/usuario")
    public Mono<User> crearUsuario(@RequestBody User user){
        return userService.save(user);
    }

}
