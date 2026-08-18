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
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
@Table(name = "insumo")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInsumo;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(unique = true)
    private String nombre;

    @Lob
    private String descripcion;

    @NotNull
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock = 0;

    @NotNull(message = "El precio unitario es obligatorio")
    @PositiveOrZero
    private BigDecimal precioUnitario;

    private String unidadMedida;
}
