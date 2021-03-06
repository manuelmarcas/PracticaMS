package com.practica.factura.services;

import com.practica.instancias_mongo.domain.Factura;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IFacturaService{

    public List<Factura> getFacturas();

    public Optional<Factura> getFacturaId(String id);

    public ResponseEntity<?> getFacturaIdCliente(Integer idCliente);

    public ResponseEntity<?> getFacturaEstado(Integer estado);

    public Boolean comprobarEstado(Integer idCliente);

    public ResponseEntity<?> findByImporte(Float importe);

    public ResponseEntity<?> save(Factura factura);

    public ResponseEntity<?> modify(Factura factura);

    public void delete(Factura factura);

    public void deleteById(String id);

}
