package com.practica.instancias.domain;

import javax.persistence.*;

@Table(name = "direcciones")
@Entity
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numero")
    private int numero;

    @Column(name = "id_cliente")
    private int idCliente;

    /*@OneToOne(cascade = CascadeType.ALL, mappedBy = "direccion")
    private Cliente cliente;*/



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

    /*public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }*/

}
