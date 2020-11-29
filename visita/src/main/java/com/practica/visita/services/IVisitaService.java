package com.practica.visita.services;

import com.practica.instancias.domain.Direccion;
import com.practica.instancias.domain.Visita;

import java.util.ArrayList;
import java.util.List;

public interface IVisitaService {

    public List<Visita> getVisitas();

    public Visita getVisitaId(int id);

    public Visita save(Visita visita);

    public void delete(Visita visita);

    public void deleteById(int id);

}
