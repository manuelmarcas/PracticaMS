package com.practica.visita.services;

import com.netflix.discovery.EurekaClient;
import com.practica.instancias.domain.Visita;
import com.practica.instancias.domain.Direccion;
import com.practica.visita.repositories.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VisitaServiceImpl implements IVisitaService {

    @Autowired
    private VisitaRepository visitaRepository;
    
    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;



    public List<Visita> getVisitas(){
        return visitaRepository.findAll();
    }

    public Visita getVisitaId(int id){
        return visitaRepository.findById(id);
    }

    public Visita save(Visita visita){
        return visitaRepository.save(visita);
    }

    public void delete(Visita visita){
        List<Visita> listaVisitas = new ArrayList<>();
        listaVisitas.add(visita);

        visitaRepository.deleteInBatch(listaVisitas);
    }

    public void deleteById(int id){
        System.out.println(id);
        Visita c = visitaRepository.findById(id);

        List<Visita> listaVisitas = new ArrayList<>();
        listaVisitas.add(c);

        visitaRepository.deleteInBatch(listaVisitas);
    }
    
}
