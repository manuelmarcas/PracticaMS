package com.practica.cliente.repositories;

import com.practica.instancias.domain.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    List<Direccion> findByCiudad(String ciudad);

}
