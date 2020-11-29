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

    public ResponseEntity<?> getFacturaIdCliente(Integer idCliente){
        List<Factura> facturas = facturaRepository.findByIdCliente(idCliente);
        List<FacturaDTO> facturasDTOs = new ArrayList<>();

        Map<String, Object> response = new HashMap<>();
        if(facturas.size() != 0){
            for(Factura f : facturas){
                Cliente cliente = buscarCliente(idCliente);
                Visita visita = buscarVisita(f.getLineaFactura());
                List<Pago> pagos = buscarPagos(f.getId());

                String formaPago = formaDePago(f.getFormaPago());
                FacturaDTO dto = new FacturaDTO(f.getId(), cliente.getNombre(), f.getImporte(),
                        formaPago, estadoFactura(f.getEstado()), pagos, visita);

                facturasDTOs.add(dto);
            }

            response.put("Mensaje", facturas.size() + " facturas obtenidas.");
            response.put("Facturas", facturasDTOs);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }else{
            response.put("Mensaje", "El cliente no tiene facturas.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> getFacturaEstado(Integer estado){
        List<Factura> facturas = facturaRepository.findByEstado(estado);
        List<FacturaDTO> facturasDTOs = new ArrayList<>();

        Map<String, Object> response = new HashMap<>();
        if(facturas.size() != 0){
            String formaPago = formaDePago(facturas.get(0).getFormaPago());
            for(Factura f : facturas){
                Cliente cliente = buscarCliente(f.getIdCliente());
                Visita visita = buscarVisita(f.getLineaFactura());
                List<Pago> pagos = buscarPagos(f.getId());

                FacturaDTO dto = new FacturaDTO(f.getId(), cliente.getNombre(), f.getImporte(),
                        formaPago, estadoFactura(f.getEstado()), pagos, visita);

                facturasDTOs.add(dto);
            }

            response.put("Mensaje", facturas.size() + " facturas obtenidas.");
            response.put("Facturas", facturasDTOs);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }else{
            response.put("Mensaje", "No hay facturas con ese estado");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

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
            String formaPago = formaDePago(f.getFormaPago());

            FacturaDTO dto = new FacturaDTO(f.getId(), c.getNombre(), f.getImporte(),
                    formaPago, estadoFactura(f.getEstado()), pagosCreados, visitaCreada);

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

    public Visita buscarVisita(Integer idVisita){

        Application applicationVisita = eurekaClient.getApplication("visita");
        List<InstanceInfo> instanceInfosVisita = applicationVisita.getInstances();

        Visita v = new Visita();

        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl = instanceInfosVisita.get(0).getHomePageUrl();
        ResponseEntity<Visita> responseVisita
                = restTemplate.getForEntity(fooResourceUrl + "api/visita/" + idVisita, Visita.class);

        v = responseVisita.getBody();

        return v;

    }

    public List<Pago> buscarPagos(String idFactura){

        Application applicationVisita = eurekaClient.getApplication("pago");
        List<InstanceInfo> instanceInfosVisita = applicationVisita.getInstances();

        List<Pago> pagos = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl = instanceInfosVisita.get(0).getHomePageUrl();
        ResponseEntity<Pago[]> responsePago
                = restTemplate.getForEntity(fooResourceUrl + "api/pago/factura/" + idFactura, Pago[].class);

        for(int i=0; i<responsePago.getBody().length; i++)
            pagos.add(responsePago.getBody()[i]);

        return pagos;

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



    public String formaDePago(Integer formaPago){
        if(formaPago == 1)
            return "Un pago";
        else if(formaPago == 2)
            return "Dos pagos";
        else
            return "Tres pagos";
    }

    public String estadoFactura(Integer estado){
        if(estado == 0)
            return "Impagada";
        else if(estado == 1)
            return "Pendiente de pago";
        else if(estado == 2)
            return "Pagada parcialmente";
        else
            return "Pagada";
    }


}
