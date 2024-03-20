package com.futbol.equipos.service;

import com.futbol.equipos.entity.Equipo;
import com.futbol.equipos.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EquipoService {

    private final EquipoRepository repository;

    @Autowired
    public EquipoService(EquipoRepository repository) {
        this.repository = repository;
    }

    public List<Equipo> findAll() {
        return repository.findAll();
    }

    public Equipo findEquipoById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public List<Equipo> findEquipoByNombre(String nombre) {
        return repository.findByNombreContaining(nombre);
    }

    public Equipo createEquipo(Equipo equipo) {
        return repository.save(equipo);
    }

    public Equipo updateEquipo(Long id, Equipo equipo) {
        return repository.findById(id)
                .map(eq -> {
                    eq.setNombre(equipo.getNombre());
                    eq.setPais(equipo.getPais());
                    eq.setLiga(equipo.getLiga());
                    return repository.save(eq);
                })
                .orElseThrow();
    }

    public void deleteEquipo(Long id) {
        if(!repository.existsById(id)){
            throw new NoSuchElementException();
        }
        repository.deleteById(id);

    }

}
