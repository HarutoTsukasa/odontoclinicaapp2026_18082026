package com.sena.clinica.odontologica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.clinica.odontologica.model.Tratamiento;
import com.sena.clinica.odontologica.repository.TratamientoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;

    public List<Tratamiento> listar() {
        return tratamientoRepository.findAll();
    }

    public Tratamiento buscarPorId(Integer id) {
        return tratamientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tratamiento no encontrado: " + id));
    }

    public Tratamiento guardar(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    public void eliminar(Integer id) {
        tratamientoRepository.deleteById(id);
    }
}
