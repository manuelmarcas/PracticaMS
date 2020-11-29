package com.practica.factura.controllers;

import com.practica.factura.services.IFacturaService;
import com.practica.instancias_mongo.domain.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/factura")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;


    @GetMapping("/todos")
    public List<Factura> getFacturas(){
        return facturaService.getFacturas();
    }

    //Optional es por si no existe
    @GetMapping("/{id}")
    public Optional<Factura> getFactura(@PathVariable("id") String id){
        return facturaService.getFacturaId(id);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> getFacturaPorIdCliente(@PathVariable("id") Integer idCliente){
        return facturaService.getFacturaIdCliente(idCliente);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getFacturaPorEstado(@PathVariable("estado") Integer estado){
        return facturaService.getFacturaEstado(estado);
    }

    @GetMapping("/importe/{importe}")
    public ResponseEntity<?> getFacturaPorImporte(@PathVariable("importe") Float importe){
        return facturaService.findByImporte(importe);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> saveFactura(@RequestBody Factura factura){
        return facturaService.save(factura);
    }

    @PutMapping("/modificar")
    public Factura modifyFactura(@RequestBody Factura factura){
        return facturaService.modify(factura);
    }

    @DeleteMapping("/eliminar")
    public void deleteFactura(@RequestBody Factura factura){
        facturaService.delete(factura);
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteFacturaById(@PathVariable("id") String id){
        facturaService.deleteById(id);
    }
}
