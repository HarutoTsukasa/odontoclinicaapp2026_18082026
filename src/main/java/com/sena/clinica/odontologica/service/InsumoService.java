package com.sena.clinica.odontologica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.clinica.odontologica.model.Insumo;
import com.sena.clinica.odontologica.repository.InsumoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    public Insumo buscarPorId(Integer id) {
        return insumoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Insumo no encontrado: " + id));
    }

    public Insumo guardar(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    public void eliminar(Integer id) {
        insumoRepository.deleteById(id);
    }
}
