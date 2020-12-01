package com.practica.entidadesdto.domain;

public class DireccionDTO {

    private Integer id;

    private String ciudad;

    private String calle;

    private int numero;

    private int idCliente;


    public DireccionDTO(Integer id, String ciudad, String calle, int numero, int idCliente) {
        this.id = id;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
        this.idCliente = idCliente;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
