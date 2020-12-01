package com.practica.gateway.services;

import com.practica.gateway.domain.User;
import reactor.core.publisher.Mono;

public interface IUserService {

    public Mono<User> save(User user);

}
