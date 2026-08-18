package com.sena.clinica.odontologica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.clinica.odontologica.model.Cita;
import com.sena.clinica.odontologica.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    Optional<Factura> findByCita(Cita cita);

    // facturas/list.html navega f.cita.paciente.nombre; sin el JOIN FETCH
    // hasta la fila de paciente, es el mismo LazyInitializationException
    // que en el dashboard, solo que un salto mas adentro (Factura -> Cita -> Paciente).
    @Query("SELECT f FROM Factura f JOIN FETCH f.cita c JOIN FETCH c.paciente ORDER BY f.fechaEmision DESC")
    List<Factura> findAllOrderByFechaEmisionDesc();
}
