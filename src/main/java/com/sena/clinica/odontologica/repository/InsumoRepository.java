package com.sena.clinica.odontologica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.clinica.odontologica.model.Insumo;

public interface InsumoRepository extends JpaRepository<Insumo, Integer> {
}
