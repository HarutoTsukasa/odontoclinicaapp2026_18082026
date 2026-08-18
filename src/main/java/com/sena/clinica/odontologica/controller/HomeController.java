package com.sena.clinica.odontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sena.clinica.odontologica.repository.CitaRepository;
import com.sena.clinica.odontologica.repository.FacturaRepository;
import com.sena.clinica.odontologica.repository.OdontologoRepository;
import com.sena.clinica.odontologica.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final CitaRepository citaRepository;
    private final FacturaRepository facturaRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalPacientes", pacienteRepository.count());
        model.addAttribute("totalOdontologos", odontologoRepository.count());
        model.addAttribute("totalCitas", citaRepository.count());
        model.addAttribute("totalFacturasPendientes",
                facturaRepository.findAll().stream()
                        .filter(f -> f.getEstadoPago().name().equals("Pendiente"))
                        .count());
        model.addAttribute("citasRecientes", citaRepository.findAllByOrderByFechaHoraDesc()
                .stream().limit(5).toList());
        return "index";
    }
}
