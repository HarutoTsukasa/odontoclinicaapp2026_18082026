package com.sena.clinica.odontologica.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "tratamiento")
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTratamiento;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(unique = true)
    private String nombre;

    @Lob
    private String descripcion;

    @NotNull(message = "El costo es obligatorio")
    @Positive(message = "El costo debe ser mayor que cero")
    private BigDecimal costo;

    private Integer duracionEstimada;
}
