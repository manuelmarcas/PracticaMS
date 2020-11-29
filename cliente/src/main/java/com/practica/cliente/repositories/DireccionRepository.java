package com.practica.cliente.repositories;

import com.practica.instancias.domain.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    //Direccion findByCiudadAndCalleAndNumero(String ciudad, String calle, int numero);

}
