package com.practica.visita.repositories;

import com.practica.instancias.domain.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaRepository extends JpaRepository<Visita, Integer> {

    Visita findById(int id);

}
