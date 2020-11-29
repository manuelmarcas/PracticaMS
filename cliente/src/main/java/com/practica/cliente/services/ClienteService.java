package com.practica.cliente.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryEvent;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.practica.cliente.repositories.ClienteRepository;
import com.practica.instancias.domain.Cliente;
import com.practica.instancias.domain.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DireccionService direccionService;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;



    public List<Cliente> getClientes(){
        return clienteRepository.findAll();
    }

    public Cliente getClienteId(int id){
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente){
        Direccion d = direccionService.save(cliente.getDireccion());
        //cliente
        return clienteRepository.save(cliente);
    }

    public void delete(Cliente cliente){
        /*int id = cliente.getId();
        Cliente c = clienteRepository.findById(id);*/
        List<Cliente> listaClientes = new ArrayList<>();
        List<Direccion> listaDirecciones = new ArrayList<>();

        listaClientes.add(cliente);
        listaDirecciones.add(cliente.getDireccion());

        clienteRepository.deleteInBatch(listaClientes);
        direccionService.delete(listaDirecciones);
    }

    public void deleteById(int id){
        System.out.println(id);
        Cliente c = clienteRepository.findById(id);

        List<Cliente> listaClientes = new ArrayList<>();
        List<Direccion> listaDirecciones = new ArrayList<>();

        listaClientes.add(c);
        listaDirecciones.add(c.getDireccion());

        clienteRepository.deleteInBatch(listaClientes);
        direccionService.delete(listaDirecciones);
    }


    public ResponseEntity<?> getClienteByCiudadAndNombre(String ciudad, String nombre){

        List<Direccion> direcciones

    }

}
