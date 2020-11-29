package com.practica.factura.repositories;

import com.practica.instancias_mongo.domain.Factura;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FacturaRepository extends MongoRepository<Factura, String> {

    List<Factura> findByIdCliente(Integer idCliente);

    List<Factura> findByEstado(Integer estado);

}
