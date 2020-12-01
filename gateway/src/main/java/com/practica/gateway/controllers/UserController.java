package com.practica.gateway.controllers;

import com.practica.gateway.domain.User;
import com.practica.gateway.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("crear/{username}/{pass}/{rol}")
    public User crearUsuario(@PathVariable("username") String username,
                             @PathVariable("pass") String pass,
                             @PathVariable("rol") String rol){
        return userService.save(username, pass, rol);
    }

}
