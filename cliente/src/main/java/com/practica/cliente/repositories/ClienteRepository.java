package com.practica.cliente.repositories;

//import com.practica.cliente.Cliente;
import com.practica.instancias.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findById(int id);

}
