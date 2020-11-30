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
import org.springframework.scheduling.annotation.Scheduled;
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

    public ResponseEntity<?> findByImporte(Float importe){
        List<Factura> facturas = facturaRepository.findByImporte(importe);
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

            response.put("Mensaje", facturas.size() + " facturas obtenidas con importe de " + importe + "€.");
            response.put("Facturas", facturasDTOs);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        }else{
            response.put("Mensaje", "No hay facturas con ese importe");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
    }

    public Boolean comprobarEstado(Integer idCliente){
        List<Factura> facturas = facturaRepository.findByIdCliente(idCliente);
        for(Factura f : facturas){
            if(f.getEstado() != 3)
                return false;
        }
        return true;
    }

    public ResponseEntity<?> save(Factura factura){

        Map<String, Object> response = new HashMap<>();
        if(factura.getFormaPago() < 1 || factura.getFormaPago() > 3){
            response.put("Mensaje", "La forma de pago solo puede ser de 1, 2 o 3 pagos.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Cliente c = buscarCliente(factura.getIdCliente());
        System.out.println("SI EJECUTA EL metodo");
        if(c != null){
            System.out.println("SI ENTRA DENTRO DEL IF");

            //SE CREA LA VISITA Y SE GUARDA
            System.out.println("1: " + factura.getImporte());
            System.out.println("1: " + factura.getIdCliente());
            Visita visitaCreada = crearVisita(factura.getImporte(), factura.getIdCliente());

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

    public ResponseEntity<?> modify(Factura factura){

        Map<String, Object> response = new HashMap<>();
        Optional<Factura> facturaAntigua = facturaRepository.findById(factura.getId());
        if(!facturaAntigua.isPresent()){
            response.put("Mensaje", "No existe ninguna factura con ese id.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(facturaAntigua.get().getEstado() != 1){
            response.put("Mensaje", "No se puede modificar la factura porque necesita tener el estado PENDIENTE.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else if(factura.getFormaPago() < 1 || factura.getFormaPago() > 3){
            response.put("Mensaje", "La forma de pago solo puede ser de 1, 2 o 3 pagos.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Cliente c = buscarCliente(factura.getIdCliente());

        if(c != null){

            Factura f = new Factura();
            factura.setEstado(1);
            factura.setLineaFactura(facturaAntigua.get().getLineaFactura());
            f = facturaRepository.save(factura);

            //BUSCA LA VISITA ASOCIADA A LA FACTURA
            Visita visita = buscarVisita(f.getLineaFactura());

            //ELIMINA LOS PAGOS ANTIGUOS
            eliminarPagos(f.getId());
            //SE CREAN LOS NUEVOS PAGOS Y SE GUARDAN
            List<Pago> pagosCreados = crearPagos(f);

            //SE FORMA EL DTO
            String formaPago = formaDePago(f.getFormaPago());

            FacturaDTO dto = new FacturaDTO(f.getId(), c.getNombre(), f.getImporte(),
                    formaPago, estadoFactura(f.getEstado()), pagosCreados, visita);

            response.put("Mensaje", "Modificada la factura correctamente");
            response.put("Factura", dto);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

        }else{
            response.put("Mensaje", "No existe ningún usuario con ese id.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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



    //METODOS DE LLAMADAS A OTROS MICROSERVICIOS
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
        List<InstanceInfo> instanceInfosPago = applicationVisita.getInstances();

        List<Pago> pagos = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl = instanceInfosPago.get(0).getHomePageUrl();
        ResponseEntity<Pago[]> responsePago
                = restTemplate.getForEntity(fooResourceUrl + "api/pago/factura/" + idFactura, Pago[].class);

        for(int i=0; i<responsePago.getBody().length; i++)
            pagos.add(responsePago.getBody()[i]);

        return pagos;

    }

    public Visita crearVisita(Float importe, Integer idCliente){

        Application applicationVisita = eurekaClient.getApplication("visita");
        List<InstanceInfo> instanceInfosVisita = applicationVisita.getInstances();

        Visita v = new Visita();
        v.setFecha(new Date());
        v.setImporte(importe);
        v.setIdCliente(idCliente);
        v.setEstado(true);

        System.out.println("2: " + v.getImporte());
        System.out.println("2: " + v.getIdCliente());

        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl = instanceInfosVisita.get(0).getHomePageUrl();
        ResponseEntity<Visita> responseVisita
                = restTemplate.postForEntity(fooResourceUrl + "api/visita/guardar", v, Visita.class);

        v = responseVisita.getBody();
        System.out.println(v.getIdCliente() + " " + v.getImporte());

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

    public void eliminarPagos(String idFactura){

        Application applicationPago = eurekaClient.getApplication("pago");
        List<InstanceInfo> instanceInfosPago = applicationPago.getInstances();

        List<Pago> pagos = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl = instanceInfosPago.get(0).getHomePageUrl();
        ResponseEntity<Pago[]> responsePago
                = restTemplate.getForEntity(fooResourceUrl + "api/pago/factura/" + idFactura, Pago[].class);

        //ELIMINA LOS GRUPOS ANTIGUOS
        for(int i=0; i<responsePago.getBody().length; i++)
            restTemplate.delete(fooResourceUrl + "api/pago/eliminar/" + responsePago.getBody()[i].getId());

    }


    //METODOS DE ESTADOS
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


    @Scheduled(cron = "59 * * * * ?")
    public void comprobarEstados(){
        List<Factura> facturas = facturaRepository.findAll();

        for(Factura f : facturas){
            List<Pago> pagos = buscarPagos(f.getId());
            Integer numeroPagados = 0;
            for(Pago p : pagos){
                if(p.getEstado() == 2)
                    numeroPagados++;
            }

            if(numeroPagados == pagos.size())
                f.setEstado(3);
            else if(numeroPagados > 0 && numeroPagados < pagos.size())
                f.setEstado(2);

            facturaRepository.save(f);
        }
    }


}
