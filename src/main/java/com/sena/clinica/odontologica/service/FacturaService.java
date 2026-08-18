package com.sena.clinica.odontologica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.clinica.odontologica.model.Cita;
import com.sena.clinica.odontologica.model.EstadoPago;
import com.sena.clinica.odontologica.model.Factura;
import com.sena.clinica.odontologica.model.MetodoPago;
import com.sena.clinica.odontologica.repository.FacturaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final CitaService citaService;

    public List<Factura> listar() {
        return facturaRepository.findAllOrderByFechaEmisionDesc();
    }

    public Factura buscarPorId(Integer id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada: " + id));
    }

    public Factura generarDesdeCita(Integer idCita, MetodoPago metodoPago) {
        Cita cita = citaService.buscarPorId(idCita);

        Factura factura = facturaRepository.findByCita(cita).orElseGet(Factura::new);
        factura.setCita(cita);
        factura.setMetodoPago(metodoPago);
        factura.setTotal(citaService.calcularTotal(cita));
        if (factura.getEstadoPago() == null) {
            factura.setEstadoPago(EstadoPago.Pendiente);
        }
        return facturaRepository.save(factura);
    }

    public void marcarComoPagada(Integer id) {
        Factura factura = buscarPorId(id);
        factura.setEstadoPago(EstadoPago.Pagado);
        facturaRepository.save(factura);
    }

    public void eliminar(Integer id) {
        facturaRepository.deleteById(id);
    }
}
