package com.sena.clinica.odontologica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.clinica.odontologica.model.Cita;
import com.sena.clinica.odontologica.model.CitaInsumo;

public interface CitaInsumoRepository extends JpaRepository<CitaInsumo, Integer> {

    // citas/detalle.html lee ci.insumo.nombre; mismo motivo que arriba.
    @Query("SELECT ci FROM CitaInsumo ci JOIN FETCH ci.insumo WHERE ci.cita = :cita")
    List<CitaInsumo> findByCita(@Param("cita") Cita cita);

    void deleteByCita(Cita cita);
}
