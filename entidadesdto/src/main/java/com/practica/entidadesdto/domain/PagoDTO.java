package com.practica.entidadesdto.domain;

import java.util.List;

public class PagoDTO {

    private String id;

    private float pago;

    private String estado;

    private String idFactura;


    public PagoDTO(String id, float pago, String estado, String idFactura) {
        this.id = id;
        this.pago = pago;
        this.estado = estado;
        this.idFactura = idFactura;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getPago() {
        return pago;
    }

    public void setPago(float pago) {
        this.pago = pago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }
}
