package com.practica.pago.repositories;

import com.practica.instancias_mongo.domain.Factura;
import com.practica.instancias_mongo.domain.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PagoRepository extends MongoRepository<Pago, String> {

}
