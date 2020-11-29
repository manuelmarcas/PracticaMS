package com.practica.instancias_mongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Factura")
public class Factura {

    private @Id String id;

    private int idCliente;

    private float importe;

    private int formaPago;

    private int lineaFactura;

    private int estado;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public int getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(int formaPago) {
        this.formaPago = formaPago;
    }
    
    public int getLineaFactura() {
        return lineaFactura;
    }

    public void setLineaFactura(int lineaFactura) {
        this.lineaFactura = lineaFactura;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

}
