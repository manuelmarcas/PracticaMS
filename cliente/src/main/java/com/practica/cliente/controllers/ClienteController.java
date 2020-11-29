package com.practica.cliente.controllers;

import com.practica.cliente.services.ClienteService;
import com.practica.instancias.domain.Cliente;
import com.practica.instancias.domain.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/todos")
    public List<Cliente> getClientes(){
        return clienteService.getClientes();
    }

    //Optional es por si no existe
    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable("id") int id){
        return clienteService.getClienteId(id);
    }

    @PostMapping("/guardar")
    public Cliente saveCliente(@RequestBody Cliente cliente){
        return clienteService.save(cliente);
    }

    @PutMapping("/modificar")
    public Cliente modifyCliente(@RequestBody Cliente cliente){
        return clienteService.save(cliente);
    }

    @DeleteMapping("/eliminar")
    public void deleteCliente(@RequestBody Cliente cliente){
        clienteService.delete(cliente);
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteClienteById(@PathVariable("id") int id){
        clienteService.deleteById(id);
    }


    /*@GetMapping("/producto/{microservicio}/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable("microservicio") String microservicio,
                                                @PathVariable("id") String id){
        return clienteService.getProducto(microservicio, id);
    }

    @PostMapping("/producto/guardar/{microservicio}")
    public ResponseEntity<Producto> guardarProducto(@PathVariable("microservicio") String microservicio,
                                                    @RequestBody Producto producto){
        return clienteService.guardarProducto(microservicio, producto);
    }*/

}
