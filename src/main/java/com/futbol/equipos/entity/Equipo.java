package com.futbol.equipos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "equipo")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del equipo no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La liga del equipo no puede estar vacía")
    private String liga;

    @NotBlank(message = "El país del equipo no puede estar vacío")
    private String pais;


}
