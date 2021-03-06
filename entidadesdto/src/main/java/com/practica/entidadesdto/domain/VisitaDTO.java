package com.practica.entidadesdto.domain;

import java.util.Date;

public class VisitaDTO {

    private int id;

    private Date fecha;

    private Float importe;

    private int idCliente;

    private boolean estado;



    public VisitaDTO() {}

    public VisitaDTO(int id, Date fecha, Float importe, int idCliente, boolean estado) {
        this.id = id;
        this.fecha = fecha;
        this.importe = importe;
        this.idCliente = idCliente;
        this.estado = estado;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
