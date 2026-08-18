package com.sena.clinica.odontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.clinica.odontologica.model.Genero;
import com.sena.clinica.odontologica.model.Paciente;
import com.sena.clinica.odontologica.service.PacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteService.listar());
        return "pacientes/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paciente", new Paciente());
        model.addAttribute("generos", Genero.values());
        return "pacientes/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("paciente", pacienteService.buscarPorId(id));
        model.addAttribute("generos", Genero.values());
        return "pacientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("paciente") Paciente paciente, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("generos", Genero.values());
            return "pacientes/form";
        }
        pacienteService.guardar(paciente);
        redirectAttributes.addFlashAttribute("mensaje", "Paciente guardado correctamente.");
        return "redirect:/pacientes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        pacienteService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Paciente eliminado.");
        return "redirect:/pacientes";
    }
}
