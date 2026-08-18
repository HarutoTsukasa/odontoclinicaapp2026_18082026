package com.sena.clinica.odontologica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.clinica.odontologica.model.Cita;
import com.sena.clinica.odontologica.model.CitaTratamiento;

public interface CitaTratamientoRepository extends JpaRepository<CitaTratamiento, Integer> {

    // citas/detalle.html lee ct.tratamiento.nombre; sin el JOIN FETCH es el
    // mismo LazyInitializationException que ya viste, ahora sobre Tratamiento.
    @Query("SELECT ct FROM CitaTratamiento ct JOIN FETCH ct.tratamiento WHERE ct.cita = :cita")
    List<CitaTratamiento> findByCita(@Param("cita") Cita cita);

    void deleteByCita(Cita cita);
}
