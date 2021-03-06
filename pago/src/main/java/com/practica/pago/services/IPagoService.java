package com.practica.pago.services;

import com.practica.instancias_mongo.domain.Pago;

import java.util.List;
import java.util.Optional;

public interface IPagoService {

    public List<Pago> getPagos();

    public Optional<Pago> getPagoId(String id);

    public Pago[] getPagoIdFactura(String idFactura);

    public Pago save(Pago pago);

    public Pago modify(Pago pago);

    public void delete(Pago pago);

    public void deleteById(String id);

}
