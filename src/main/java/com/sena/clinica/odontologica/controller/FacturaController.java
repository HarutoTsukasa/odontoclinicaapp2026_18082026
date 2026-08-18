package com.sena.clinica.odontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sena.clinica.odontologica.model.MetodoPago;
import com.sena.clinica.odontologica.service.CitaService;
import com.sena.clinica.odontologica.service.FacturaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;
    private final CitaService citaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", facturaService.listar());
        return "facturas/list";
    }

    @GetMapping("/generar/{idCita}")
    public String formularioGenerar(@PathVariable Integer idCita, Model model) {
        model.addAttribute("cita", citaService.buscarPorId(idCita));
        model.addAttribute("total", citaService.calcularTotal(citaService.buscarPorId(idCita)));
        model.addAttribute("metodosPago", MetodoPago.values());
        return "facturas/form";
    }

    @PostMapping("/generar/{idCita}")
    public String generar(@PathVariable Integer idCita, @RequestParam MetodoPago metodoPago,
            RedirectAttributes redirectAttributes) {
        facturaService.generarDesdeCita(idCita, metodoPago);
        redirectAttributes.addFlashAttribute("mensaje", "Factura generada correctamente.");
        return "redirect:/facturas";
    }

    @PostMapping("/{id}/pagar")
    public String marcarComoPagada(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        facturaService.marcarComoPagada(id);
        redirectAttributes.addFlashAttribute("mensaje", "Factura marcada como pagada.");
        return "redirect:/facturas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        facturaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Factura eliminada.");
        return "redirect:/facturas";
    }
}
