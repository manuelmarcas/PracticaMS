package com.practica.factura.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.practica.factura.repositories.FacturaRepository;
import com.practica.instancias.DTOs.FacturaDTO;
import com.practica.instancias.domain.Cliente;
import com.practica.instancias.domain.Visita;
import com.practica.instancias_mongo.domain.Factura;
import com.practica.instancias_mongo.domain.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FacturaServiceImp implements IFacturaService {

    @Autowired
    private FacturaRepository facturaRepository;


    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;



    public List<Factura> getFacturas(){
        return facturaRepository.findAll();
    }

    public Optional<Factura> getFacturaId(String id){
        return facturaRepository.findById(id);
    }

    public ResponseEntity<?> save(Factura factura){

        Map<String, Object> response = new HashMap<>();
        if(factura.getFormaPago() < 1 || factura.getFormaPago() > 3){
            response.put("Mensaje", "La forma de pago solo puede ser de 1, 2 o 3 pagos.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Cliente c = buscarCliente(factura.getIdCliente());

        if(c != null){

            //SE CREA LA VISITA Y SE GUARDA
            Visita visitaCreada = crearVisita();

            //SE GUARDA LA FACTURA
            Factura f = new Factura();
            factura.setLineaFactura(visitaCreada.getId());
            factura.setEstado(1);
            f = facturaRepository.save(factura);

            //SE CREAN LOS PAGOS Y SE GUARDAN
            List<Pago> pagosCreados = crearPagos(f);

            //SE FORMA EL DTO
            String formaPago;
            if(f.getFormaPago() == 1)
                formaPago = "Un pago";
            else if(f.getFormaPago() == 1)
                formaPago = "Dos pagos";
            else
                formaPago = "Tres pagos";

            FacturaDTO dto = new FacturaDTO(f.getId(), c.getNombre(), f.getImporte(),
                    formaPago, "Pendiente de pago", pagosCreados, visitaCreada);

            response.put("Mensaje", "Creada con exito la factura junto con los pagos y la visita");
            response.put("Factura", dto);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        }else{
            response.put("Mensaje", "No existe ningún usuario con ese id.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public Factura modify(Factura factura){
        return facturaRepository.save(factura);
    }

    public void delete(Factura factura){
        facturaRepository.delete(factura);
    }

    public void deleteById(String id){
        System.out.println(id);
        Optional<Factura> f = facturaRepository.findById(id);

        if(f!=null){
            Factura factura = new Factura();
            factura.setId(f.get().getId());
            factura.setIdCliente(f.get().getIdCliente());
            factura.setLineaFactura(f.get().getLineaFactura());

            facturaRepository.delete(factura);
        }

        
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


    public Visita crearVisita(){

        Application applicationVisita = eurekaClient.getApplication("visita");
        List<InstanceInfo> instanceInfosVisita = applicationVisita.getInstances();

        Visita v = new Visita();
        v.setFecha(new Date());
        v.setEstado(true);

        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl = instanceInfosVisita.get(0).getHomePageUrl();
        ResponseEntity<Visita> responseVisita
                = restTemplate.postForEntity(fooResourceUrl + "api/visita/guardar", v, Visita.class);

        v = responseVisita.getBody();
        System.out.println(v.getFecha() + ", " + v.getId());

        return v;

    }

    public List<Pago> crearPagos(Factura factura){

        Application applicationPago = eurekaClient.getApplication("pago");
        List<InstanceInfo> instanceInfosPago = applicationPago.getInstances();

        Pago p = new Pago();
        p.setIdFactura(factura.getId());
        p.setEstado(1);
        float pagos = factura.getImporte() / (float)factura.getFormaPago();
        p.setPago(pagos);

        List<Pago> pagosCreados = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = instanceInfosPago.get(0).getHomePageUrl();
        fooResourceUrl = instanceInfosPago.get(0).getHomePageUrl();
        for(int i=0; i<factura.getFormaPago(); i++) {
            ResponseEntity<Pago> responsePago
                    = restTemplate.postForEntity(fooResourceUrl + "api/pago/guardar", p, Pago.class);
            pagosCreados.add(responsePago.getBody());
        }

        return pagosCreados;

    }


}
