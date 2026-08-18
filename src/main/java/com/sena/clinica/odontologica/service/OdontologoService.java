package com.sena.clinica.odontologica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.clinica.odontologica.model.Odontologo;
import com.sena.clinica.odontologica.repository.OdontologoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OdontologoService {

    private final OdontologoRepository odontologoRepository;

    public List<Odontologo> listar() {
        return odontologoRepository.findAll();
    }

    public Odontologo buscarPorId(Integer id) {
        return odontologoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Odontologo no encontrado: " + id));
    }

    public Odontologo guardar(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public void eliminar(Integer id) {
        odontologoRepository.deleteById(id);
    }
}
