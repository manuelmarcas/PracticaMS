package com.practica.visita.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.practica.entidadesdto.domain.VisitaDTO;
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

    public VisitaDTO getVisitaId(int id){
        Visita v = visitaRepository.findById(id);
        return new VisitaDTO(v.getId(), v.getFecha(), v.getImporte(), v.getIdCliente(), v.getEstado());
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

    public VisitaDTO save(VisitaDTO visita){
        Visita v = new Visita();
        v.setFecha(visita.getFecha());
        v.setImporte(visita.getImporte());
        v.setIdCliente(visita.getIdCliente());
        v.setEstado(visita.getEstado());
        v = visitaRepository.save(v);

        visita.setId(v.getId());
        return visita;
    }

    public ResponseEntity<?> modify(Visita visita){

        Map<String, Object> response = new HashMap<>();
        Optional<Visita> visitaAntigua = visitaRepository.findById(visita.getId());

        if(!visitaAntigua.get().getEstado()){
            visita.setEstado(false);
            visita = visitaRepository.save(visita);
            response.put("Mensaje", "Visita actualizada.");
            response.put("Visitas", visita);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }else{
            response.put("Mensaje", "Solo se pueden actualizar las visitas no facturadas (estado = 0).");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
