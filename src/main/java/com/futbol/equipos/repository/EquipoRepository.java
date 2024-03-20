package com.futbol.equipos.repository;

import com.futbol.equipos.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByNombreContaining (String nombre);
}

