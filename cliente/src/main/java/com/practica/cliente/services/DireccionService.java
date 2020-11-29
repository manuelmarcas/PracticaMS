package com.practica.cliente.services;

import com.practica.cliente.repositories.ClienteRepository;
import com.practica.cliente.repositories.DireccionRepository;
import com.practica.instancias.domain.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;


    public Direccion save(Direccion d){
        return direccionRepository.save(d);
    }

    public void delete(List<Direccion> direcciones){
        direccionRepository.deleteInBatch(direcciones);
    }
    /*public Direccion findByCiudadAndCalleAndNumero(String ciudad, String calle, int numero){
        return direccionRepository.findByCiudadAndCalleAndNumero(ciudad, calle, numero);
    }*/

}
