package com.practica.visita.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.practica.instancias.domain.Cliente;
import com.practica.instancias.domain.Visita;
import com.practica.instancias.domain.Direccion;
import com.practica.visita.repositories.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    public ResponseEntity<?> getVisitaByIdCliente(int idCliente){

        Cliente c = buscarCliente(idCliente);
        List<Visita> visitas = visitaRepository.findByIdCliente(idCliente);
        Map<String, Object> response = new HashMap<>();

        if(c == null){
            response.put("Mensaje", "No hay ningún usuario con ese id");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(visitas.size() != 0){
            response.put("Mensaje", "El usuario " + c.getNombre() + " tiene " + visitas.size() + " visitas.");
            response.put("Visitas", visitas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }else{
            response.put("Mensaje", "No hay ninguna visita de ese usuario.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> getVisitaByEstado(Integer estado){

        List<Visita> visitas = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();



        if(estado == 0){
            visitas = visitaRepository.findByEstado(false);
            response.put("Mensaje", "Hay " + visitas.size() + " con el estado NO FACTURADA.");
            response.put("Visitas", visitas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }else{
            visitas = visitaRepository.findByEstado(true);
            response.put("Mensaje", "Hay " + visitas.size() + " con el estado FACTURADA.");
            response.put("Visitas", visitas);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }

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




    public Cliente buscarCliente(Integer idCliente){

        Application applicationCliente = eurekaClient.getApplication("cliente");
        List<InstanceInfo> instanceInfosCliente = applicationCliente.getInstances();

        String fooResourceUrl = instanceInfosCliente.get(0).getHomePageUrl();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Cliente> responseCliente
                = restTemplate.getForEntity(fooResourceUrl + "api/cliente/" + idCliente, Cliente.class);
        Cliente c = responseCliente.getBody();

        return c;
    }
    
}
