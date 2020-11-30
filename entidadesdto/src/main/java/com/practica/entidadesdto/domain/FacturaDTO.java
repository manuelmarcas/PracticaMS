package com.practica.entidadesdto.domain;

import java.util.List;

public class FacturaDTO {


    private String id;

    private String nombreCliente;

    private float importe;

    private String formaPago;

    private String estado;

    private List<PagoDTO> pagos;

    private VisitaDTO visita;


    public FacturaDTO(String id, String nombreCliente, float importe, String formaPago, String estado, List<PagoDTO> pagos, VisitaDTO visita) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.importe = importe;
        this.formaPago = formaPago;
        this.estado = estado;
        this.pagos = pagos;
        this.visita = visita;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<PagoDTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoDTO> pagos) {
        this.pagos = pagos;
    }

    public VisitaDTO getVisita() {
        return visita;
    }

    public void setVisita(VisitaDTO visita) {
        this.visita = visita;
    }
}
