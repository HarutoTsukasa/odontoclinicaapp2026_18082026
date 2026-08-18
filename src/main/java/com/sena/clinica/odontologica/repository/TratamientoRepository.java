package com.sena.clinica.odontologica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.clinica.odontologica.model.Tratamiento;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
}
