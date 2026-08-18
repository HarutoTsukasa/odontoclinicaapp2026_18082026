package com.sena.clinica.odontologica.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.sena.clinica.odontologica.model.Odontologo;

@Component
public class OdontologoToStringConverter implements Converter<Odontologo, String> {

    @Override
    public String convert(Odontologo source) {
        return source.getIdOdontologo() == null ? "" : String.valueOf(source.getIdOdontologo());
    }
}
