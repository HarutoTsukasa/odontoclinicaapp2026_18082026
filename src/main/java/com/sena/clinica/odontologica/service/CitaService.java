package com.sena.clinica.odontologica.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.clinica.odontologica.model.Cita;
import com.sena.clinica.odontologica.model.CitaInsumo;
import com.sena.clinica.odontologica.model.CitaTratamiento;
import com.sena.clinica.odontologica.model.Insumo;
import com.sena.clinica.odontologica.model.Tratamiento;
import com.sena.clinica.odontologica.repository.CitaInsumoRepository;
import com.sena.clinica.odontologica.repository.CitaRepository;
import com.sena.clinica.odontologica.repository.CitaTratamientoRepository;
import com.sena.clinica.odontologica.repository.InsumoRepository;
import com.sena.clinica.odontologica.repository.TratamientoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final CitaTratamientoRepository citaTratamientoRepository;
    private final CitaInsumoRepository citaInsumoRepository;
    private final TratamientoRepository tratamientoRepository;
    private final InsumoRepository insumoRepository;

    public List<Cita> listar() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }

    public Cita buscarPorId(Integer id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada: " + id));
    }

    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Transactional
    public void eliminar(Integer id) {
        Cita cita = buscarPorId(id);
        citaTratamientoRepository.deleteByCita(cita);
        citaInsumoRepository.deleteByCita(cita);
        citaRepository.delete(cita);
    }

    // --- Tratamientos aplicados en la cita ---

    public List<CitaTratamiento> listarTratamientos(Cita cita) {
        return citaTratamientoRepository.findByCita(cita);
    }

    @Transactional
    public void agregarTratamiento(Integer idCita, Integer idTratamiento, Integer cantidad) {
        Cita cita = buscarPorId(idCita);
        Tratamiento tratamiento = tratamientoRepository.findById(idTratamiento)
                .orElseThrow(() -> new IllegalArgumentException("Tratamiento no encontrado: " + idTratamiento));

        CitaTratamiento ct = new CitaTratamiento();
        ct.setCita(cita);
        ct.setTratamiento(tratamiento);
        ct.setCantidad(cantidad);
        ct.setCostoAplicado(tratamiento.getCosto().multiply(BigDecimal.valueOf(cantidad)));
        citaTratamientoRepository.save(ct);
    }

    public void quitarTratamiento(Integer idCitaTratamiento) {
        citaTratamientoRepository.deleteById(idCitaTratamiento);
    }

    // --- Insumos usados en la cita (con descuento de stock) ---

    public List<CitaInsumo> listarInsumos(Cita cita) {
        return citaInsumoRepository.findByCita(cita);
    }

    @Transactional
    public void agregarInsumo(Integer idCita, Integer idInsumo, Integer cantidad) {
        Cita cita = buscarPorId(idCita);
        Insumo insumo = insumoRepository.findById(idInsumo)
                .orElseThrow(() -> new IllegalArgumentException("Insumo no encontrado: " + idInsumo));

        if (insumo.getStock() < cantidad) {
            throw new IllegalStateException(
                    "Stock insuficiente de " + insumo.getNombre() + " (disponible: " + insumo.getStock() + ")");
        }

        CitaInsumo ci = new CitaInsumo();
        ci.setCita(cita);
        ci.setInsumo(insumo);
        ci.setCantidad(cantidad);
        citaInsumoRepository.save(ci);

        insumo.setStock(insumo.getStock() - cantidad);
        insumoRepository.save(insumo);
    }

    @Transactional
    public void quitarInsumo(Integer idCitaInsumo) {
        CitaInsumo ci = citaInsumoRepository.findById(idCitaInsumo)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado: " + idCitaInsumo));
        Insumo insumo = ci.getInsumo();
        insumo.setStock(insumo.getStock() + ci.getCantidad());
        insumoRepository.save(insumo);
        citaInsumoRepository.deleteById(idCitaInsumo);
    }

    public BigDecimal calcularTotal(Cita cita) {
        return listarTratamientos(cita).stream()
                .map(CitaTratamiento::getCostoAplicado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
