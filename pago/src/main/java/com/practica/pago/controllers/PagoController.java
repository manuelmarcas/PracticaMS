package com.practica.pago.controllers;

import com.practica.instancias_mongo.domain.Pago;
import com.practica.pago.services.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired
    private IPagoService pagoService;


    @GetMapping("/todos")
    public List<Pago> getPagos(){
        return pagoService.getPagos();
    }

    //Optional es por si no existe
    @GetMapping("/{id}")
    public Optional<Pago> getPago(@PathVariable("id") String id){
        return pagoService.getPagoId(id);
    }

    @GetMapping("/factura/{id}")
    public Pago[] getPagoIdFactura(@PathVariable("id") String idFactura){
        return pagoService.getPagoIdFactura(idFactura);
    }


    @PostMapping("/guardar")
    public Pago savePago(@RequestBody Pago pago){
        return pagoService.save(pago);
    }

    @PutMapping("/modificar")
    public Pago modifyPago(@RequestBody Pago pago){
        return pagoService.save(pago);
    }

    @DeleteMapping("/eliminar")
    public void deletePago(@RequestBody Pago pago){
        pagoService.delete(pago);
    }

    @DeleteMapping("/eliminar/{id}")
    public void deletePagoById(@PathVariable("id") String id){
        pagoService.deleteById(id);
    }
}
