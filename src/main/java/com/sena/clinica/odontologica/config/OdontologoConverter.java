package com.sena.clinica.odontologica.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sena.clinica.odontologica.model.Odontologo;
import com.sena.clinica.odontologica.repository.OdontologoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OdontologoConverter implements Converter<String, Odontologo> {

    private final OdontologoRepository odontologoRepository;

    @Override
    public Odontologo convert(String source) {
        if (!StringUtils.hasText(source)) {
            return null;
        }
        return odontologoRepository.findById(Integer.valueOf(source)).orElse(null);
    }
}
