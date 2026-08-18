package com.sena.clinica.odontologica.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sena.clinica.odontologica.model.Paciente;
import com.sena.clinica.odontologica.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

// Permite que un <select> en un formulario Thymeleaf, cuyo valor es el id del
// paciente (String), se enlace directamente al campo "Paciente paciente" de Cita.
@Component
@RequiredArgsConstructor
public class PacienteConverter implements Converter<String, Paciente> {

    private final PacienteRepository pacienteRepository;

    @Override
    public Paciente convert(String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        return pacienteRepository.findById(Integer.valueOf(source)).orElse(null);
    }
}
