package com.practica.factura.repositories;

import com.practica.instancias_mongo.domain.Factura;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacturaRepository extends MongoRepository<Factura, String> {

}
