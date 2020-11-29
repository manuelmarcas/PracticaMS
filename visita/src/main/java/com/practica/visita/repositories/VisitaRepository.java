package com.practica.visita.repositories;

import com.practica.instancias.domain.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitaRepository extends JpaRepository<Visita, Integer> {

    Visita findById(int id);

    List<Visita> findByIdCliente(int idCliente);

}
