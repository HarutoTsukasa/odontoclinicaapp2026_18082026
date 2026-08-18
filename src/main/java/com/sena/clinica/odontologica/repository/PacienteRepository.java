package com.sena.clinica.odontologica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.clinica.odontologica.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
