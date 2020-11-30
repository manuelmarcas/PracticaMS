package com.practica.visita.controllers;

import com.practica.instancias.domain.Visita;
import com.practica.visita.services.IVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visita")
public class VisitaController {

    @Autowired
    private IVisitaService visitaService;


    @GetMapping("/todos")
    public List<Visita> getVisitas(){
        return visitaService.getVisitas();
    }

    //Optional es por si no existe
    @GetMapping("/{id}")
    public Visita getVisita(@PathVariable("id") int id){
        return visitaService.getVisitaId(id);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> getVisitaCliente(@PathVariable("id") int idCliente){
        return visitaService.getVisitaByIdCliente(idCliente);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getVisitaCliente(@PathVariable("estado") Integer estado){
        return visitaService.getVisitaByEstado(estado);
    }

    @PostMapping("/guardar")
    public Visita saveVisita(@RequestBody Visita visita){
        return visitaService.save(visita);
    }

    @PutMapping("/modificar")
    public Visita modifyVisita(@RequestBody Visita visita){
        return visitaService.save(visita);
    }

    @DeleteMapping("/eliminar")
    public void deleteVisita(@RequestBody Visita visita){
        visitaService.delete(visita);
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteVisitaById(@PathVariable("id") int id){
        visitaService.deleteById(id);
    }
    
}
