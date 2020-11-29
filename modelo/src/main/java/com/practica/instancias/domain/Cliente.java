package com.practica.instancias.domain;

import javax.persistence.*;

@Table(name = "clientes")
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "estado")
    private int estado;

    @OneToOne
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;

    //private int idDireccion;



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

   public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

   /*public Integer getIdDireccion() {
       return idDireccion;
   }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }*/
}
