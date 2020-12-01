package com.practica.gateway.domain;

import org.springframework.security.core.GrantedAuthority;

public class Rol implements GrantedAuthority {

    private String rol;

    @Override
    public String getAuthority() {
        return rol;
    }


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
