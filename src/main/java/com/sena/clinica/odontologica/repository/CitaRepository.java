package com.sena.clinica.odontologica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.clinica.odontologica.model.Cita;
import com.sena.clinica.odontologica.model.Odontologo;
import com.sena.clinica.odontologica.model.Paciente;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // Se sobreescribe el findById heredado: sin este JOIN FETCH, cualquier
    // vista que lea cita.paciente o cita.odontologo revienta con
    // LazyInitializationException porque open-in-view=false cierra la
    // sesion de Hibernate apenas termina el metodo del controlador.
    @Override
    @Query("SELECT c FROM Cita c JOIN FETCH c.paciente JOIN FETCH c.odontologo WHERE c.idCita = :idCita")
    Optional<Cita> findById(@Param("idCita") Integer idCita);

    @Query("SELECT c FROM Cita c JOIN FETCH c.paciente JOIN FETCH c.odontologo ORDER BY c.fechaHora DESC")
    List<Cita> findAllByOrderByFechaHoraDesc();

    List<Cita> findByPaciente(Paciente paciente);

    List<Cita> findByOdontologo(Odontologo odontologo);
}
