package com.sena.clinica.odontologica.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.sena.clinica.odontologica.model.Paciente;

// Complemento de PacienteConverter (String -> Paciente). Sin este sentido
// inverso, Thymeleaf no siempre logra comparar el Paciente ya asignado a la
// cita contra el valor de cada <option> para marcar el seleccionado al
// editar: usa @Data por defecto para toString(), que no coincide con el id
// plano que llevan los value de las opciones.
@Component
public class PacienteToStringConverter implements Converter<Paciente, String> {

    @Override
    public String convert(Paciente source) {
        return source.getIdPaciente() == null ? "" : String.valueOf(source.getIdPaciente());
    }
}
