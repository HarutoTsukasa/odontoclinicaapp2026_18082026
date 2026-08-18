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

import com.sena.clinica.odontologica.model.Insumo;
import com.sena.clinica.odontologica.service.InsumoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("insumos", insumoService.listar());
        return "insumos/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("insumo", new Insumo());
        return "insumos/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("insumo", insumoService.buscarPorId(id));
        return "insumos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("insumo") Insumo insumo, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "insumos/form";
        }
        insumoService.guardar(insumo);
        redirectAttributes.addFlashAttribute("mensaje", "Insumo guardado correctamente.");
        return "redirect:/insumos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        insumoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Insumo eliminado.");
        return "redirect:/insumos";
    }
}
