package com.practica.pago.services;

import com.netflix.discovery.EurekaClient;
import com.practica.instancias_mongo.domain.Pago;
import com.practica.pago.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImp implements IPagoService {

    @Autowired
    private PagoRepository pagoRepository;


    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;



    public List<Pago> getPagos(){
        return pagoRepository.findAll();
    }

    public Optional<Pago> getPagoId(String id){
        return pagoRepository.findById(id);
    }

    public Pago[] getPagoIdFactura(String idFactura){
        return pagoRepository.findByIdFactura(idFactura);
    }

    public Pago save(Pago Pago){
        return pagoRepository.save(Pago);
    }

    public void delete(Pago Pago){
        pagoRepository.delete(Pago);
    }

    public void deleteById(String id){
        pagoRepository.deleteById(id);
    }

}
