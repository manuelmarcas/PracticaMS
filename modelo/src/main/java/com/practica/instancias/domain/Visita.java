package com.practica.instancias.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "visitas")
@Entity
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "estado")
    private Boolean estado;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
}
