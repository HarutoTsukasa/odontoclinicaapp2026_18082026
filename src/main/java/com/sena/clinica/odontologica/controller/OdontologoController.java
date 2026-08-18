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

import com.sena.clinica.odontologica.model.Odontologo;
import com.sena.clinica.odontologica.service.OdontologoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/odontologos")
@RequiredArgsConstructor
public class OdontologoController {

    private final OdontologoService odontologoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("odontologos", odontologoService.listar());
        return "odontologos/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("odontologo", new Odontologo());
        return "odontologos/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("odontologo", odontologoService.buscarPorId(id));
        return "odontologos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("odontologo") Odontologo odontologo, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "odontologos/form";
        }
        odontologoService.guardar(odontologo);
        redirectAttributes.addFlashAttribute("mensaje", "Odontologo guardado correctamente.");
        return "redirect:/odontologos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        odontologoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Odontologo eliminado.");
        return "redirect:/odontologos";
    }
}
