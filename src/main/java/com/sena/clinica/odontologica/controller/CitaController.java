package com.sena.clinica.odontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.clinica.odontologica.model.Cita;
import com.sena.clinica.odontologica.model.Estado;
import com.sena.clinica.odontologica.service.CitaService;
import com.sena.clinica.odontologica.service.InsumoService;
import com.sena.clinica.odontologica.service.OdontologoService;
import com.sena.clinica.odontologica.service.PacienteService;
import com.sena.clinica.odontologica.service.TratamientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    private final TratamientoService tratamientoService;
    private final InsumoService insumoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listar());
        return "citas/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("cita", new Cita());
        cargarListasApoyo(model);
        return "citas/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("cita", citaService.buscarPorId(id));
        cargarListasApoyo(model);
        return "citas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cita") Cita cita, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            cargarListasApoyo(model);
            return "citas/form";
        }
        Cita guardada = citaService.guardar(cita);
        redirectAttributes.addFlashAttribute("mensaje", "Cita guardada correctamente.");
        return "redirect:/citas/detalle/" + guardada.getIdCita();
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        citaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Cita eliminada.");
        return "redirect:/citas";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        Cita cita = citaService.buscarPorId(id);
        model.addAttribute("cita", cita);
        model.addAttribute("tratamientosAplicados", citaService.listarTratamientos(cita));
        model.addAttribute("insumosUsados", citaService.listarInsumos(cita));
        model.addAttribute("total", citaService.calcularTotal(cita));
        model.addAttribute("tratamientos", tratamientoService.listar());
        model.addAttribute("insumos", insumoService.listar());
        return "citas/detalle";
    }

    @PostMapping("/detalle/{id}/tratamiento")
    public String agregarTratamiento(@PathVariable Integer id,
            @RequestParam Integer idTratamiento, @RequestParam Integer cantidad,
            RedirectAttributes redirectAttributes) {
        citaService.agregarTratamiento(id, idTratamiento, cantidad);
        redirectAttributes.addFlashAttribute("mensaje", "Tratamiento agregado a la cita.");
        return "redirect:/citas/detalle/" + id;
    }

    @PostMapping("/detalle/{id}/tratamiento/{idCitaTratamiento}/eliminar")
    public String quitarTratamiento(@PathVariable Integer id, @PathVariable Integer idCitaTratamiento,
            RedirectAttributes redirectAttributes) {
        citaService.quitarTratamiento(idCitaTratamiento);
        redirectAttributes.addFlashAttribute("mensaje", "Tratamiento removido de la cita.");
        return "redirect:/citas/detalle/" + id;
    }

    @PostMapping("/detalle/{id}/insumo")
    public String agregarInsumo(@PathVariable Integer id,
            @RequestParam Integer idInsumo, @RequestParam Integer cantidad,
            RedirectAttributes redirectAttributes) {
        try {
            citaService.agregarInsumo(id, idInsumo, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Insumo registrado en la cita.");
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/citas/detalle/" + id;
    }

    @PostMapping("/detalle/{id}/insumo/{idCitaInsumo}/eliminar")
    public String quitarInsumo(@PathVariable Integer id, @PathVariable Integer idCitaInsumo,
            RedirectAttributes redirectAttributes) {
        citaService.quitarInsumo(idCitaInsumo);
        redirectAttributes.addFlashAttribute("mensaje", "Insumo removido y stock restaurado.");
        return "redirect:/citas/detalle/" + id;
    }

    private void cargarListasApoyo(Model model) {
        model.addAttribute("pacientes", pacienteService.listar());
        model.addAttribute("odontologos", odontologoService.listar());
        model.addAttribute("estados", Estado.values());
    }
}
