package com.futbol.equipos.controller;

import com.futbol.equipos.entity.Equipo;
import com.futbol.equipos.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoService service;

    @Autowired
    public EquipoController(EquipoService service) {
        this.service = service;
    }

    @Tag(name = "Equipo")
    @Operation(summary = "Obtiene la lista con todos los equipos")
    @GetMapping
    public List<Equipo> findAll() {
        return service.findAll();
    }

    @Tag(name = "Equipo")
    @Operation(summary = "Crea un equipo nuevo")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Equipo> addEquipo(@Valid @RequestBody Equipo equipo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEquipo(equipo));
    }

    @Tag(name = "Equipo")
    @Operation(summary = "Busca un equipo por Id")
    @GetMapping("/{id}")
    public Equipo findEquipoById(@PathVariable Long id) {
        return service.findEquipoById(id);
    }

    @Tag(name = "Equipo")
    @Operation(summary = "Actualiza/modifica la información de un equipo")
    @PutMapping("/{id}")
    public Equipo updateEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        return service.updateEquipo(id, equipo);
    }

    @Tag(name = "Equipo")
    @Operation(summary = "Elimina un equipo por su Id")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEquipo(@PathVariable Long id) {
        service.deleteEquipo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Tag(name = "Equipo")
    @Operation(summary = "Busca los equipos que contenga la palabar buscada")
    @GetMapping("/buscar")
    public List<Equipo> findEquiposByNombre(@RequestParam String nombre) {
        return service.findEquipoByNombre(nombre);
    }
}
