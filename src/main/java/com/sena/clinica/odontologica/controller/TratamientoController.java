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

import com.sena.clinica.odontologica.model.Tratamiento;
import com.sena.clinica.odontologica.service.TratamientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/tratamientos")
@RequiredArgsConstructor
public class TratamientoController {

    private final TratamientoService tratamientoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tratamientos", tratamientoService.listar());
        return "tratamientos/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("tratamiento", new Tratamiento());
        return "tratamientos/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("tratamiento", tratamientoService.buscarPorId(id));
        return "tratamientos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("tratamiento") Tratamiento tratamiento, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tratamientos/form";
        }
        tratamientoService.guardar(tratamiento);
        redirectAttributes.addFlashAttribute("mensaje", "Tratamiento guardado correctamente.");
        return "redirect:/tratamientos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        tratamientoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Tratamiento eliminado.");
        return "redirect:/tratamientos";
    }
}
