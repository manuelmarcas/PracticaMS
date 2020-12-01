package com.practica.entidadesdto.domain;

public class ClienteDTO {


    private Integer id;

    private String nombre;

    private int estado;

    private DireccionDTO direccion;


    public ClienteDTO(Integer id, String nombre, int estado, DireccionDTO direccion) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.direccion = direccion;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public DireccionDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDTO direccion) {
        this.direccion = direccion;
    }
}
