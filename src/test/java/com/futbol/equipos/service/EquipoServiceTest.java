package com.futbol.equipos.service;

import com.futbol.equipos.entity.Equipo;
import com.futbol.equipos.repository.EquipoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

class EquipoServiceTest {

    EquipoRepository repository;
    EquipoService service;

    private Equipo getBelgrano() {
        return new Equipo(1L, "Belgrano", "AFA", "Argentina");
    }
    private Equipo getTalleres() { return new Equipo(2L, "Talleres", "AFA", "Argentina"); }
    private Equipo getDefensores() { return new Equipo(3L, "Defensores de Belgrano", "AFA", "Argentina"); }

    @BeforeEach
    public void initTest(){
        repository = mock(EquipoRepository.class);
        service = new EquipoService(repository);
    };

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(getBelgrano()));
        List<Equipo> testResult = service.findAll();
        Assertions.assertEquals(1, testResult.size());
        Assertions.assertEquals("Belgrano", testResult.get(0).getNombre());
    }

    @Test
    void findAll_noElements() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Equipo> testResult = service.findAll();
        Assertions.assertEquals(Collections.emptyList(), testResult);
    }

    @Test
    void findEquipoById() {
        when(repository.findById(1L)).thenReturn(Optional.of(getBelgrano()));
        Equipo equipo = service.findEquipoById(1L);
        Assertions.assertNotNull(equipo);
        Assertions.assertEquals("Belgrano", equipo.getNombre());
    }

    @Test
    void findEquipoById_notFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.findEquipoById(2L));
    }

    @Test
    void findEquipoByNombre() {
        List<Equipo> equipos = Arrays.asList(getBelgrano(), getDefensores());
        when(repository.findByNombreContaining("Belgrano")).thenReturn(equipos);
        List<Equipo> testResult = service.findEquipoByNombre("Belgrano");
        Assertions.assertEquals(3, testResult.size());
        Assertions.assertEquals("Belgrano", testResult.get(0).getNombre());
    }

    @Test
    void findEquipoByNombre_notFound() {
        when(repository.findByNombreContaining("Desconocido")).thenReturn(Collections.emptyList());
        List<Equipo> testResult = service.findEquipoByNombre("Desconocido");
        Assertions.assertEquals(Collections.emptyList(), testResult);
    }

    @Test
    void createEquipo() {
        Equipo nuevoEquipo = new Equipo(null, "Boca Juniors", "AFA", "Argentina");
        Equipo equipoCreado = new Equipo(4L, "Boca Juniors", "AFA", "Argentina");
        when(repository.save(nuevoEquipo)).thenReturn(equipoCreado);
        Equipo testResult = service.createEquipo(nuevoEquipo);
        Assertions.assertNotNull(testResult);
        Assertions.assertEquals(4L, testResult.getId());
    }

    @Test
    void updateEquipo() {
        Equipo equipoToUpdate = getTalleres();
        equipoToUpdate.setNombre("Pachuca");
        Equipo equipoUpdated = new Equipo(2L, "Pachuca", "AFA", "Argentina");
        when(repository.findById(2L)).thenReturn(Optional.of(equipoToUpdate));
        when(repository.save(equipoToUpdate)).thenReturn(equipoUpdated);
        Equipo testResult = service.updateEquipo(2L, equipoToUpdate);
        Assertions.assertNotNull(testResult);
        Assertions.assertEquals("Pachuca", testResult.getNombre());
    }

    @Test
    void updateEquipo_notFound() {
        Equipo equipoToUpdate = new Equipo(4L, "Boca Juniors", "AFA", "Argentina");
        when(repository.findById(4L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> service.updateEquipo(2L, equipoToUpdate));
    }

    @Test
    void deleteEquipo() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deleteEquipo(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteEquipo_notFound() {
        when(repository.existsById(5L)).thenReturn(false);
        Assertions.assertThrows(NoSuchElementException.class, () -> service.deleteEquipo(5L));
    }
}