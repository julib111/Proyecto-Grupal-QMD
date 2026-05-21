package com.edu.uniquindio.co.qmd;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.uniquindio.co.qmd.EnumsEstados.EstadoCabana;
import com.edu.uniquindio.co.qmd.EnumsEstados.EstadoCupon;
import com.edu.uniquindio.co.qmd.EnumsEstados.EstadoReserva;
import com.edu.uniquindio.co.qmd.EnumsEstados.MetodoPago;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CheckInService {

    private final ReservaRepository reservaRepo;
    private final CabanaRepository cabanaRepo;
    private final CuponRepository cuponRepo;
    private final PagoRepository pagoRepo;

    // Inyección de dependencias vía constructor
    public CheckInService(ReservaRepository reservaRepo, CabanaRepository cabanaRepo, 
                          CuponRepository cuponRepo, PagoRepository pagoRepo) {
        this.reservaRepo = reservaRepo;
        this.cabanaRepo = cabanaRepo;
        this.cuponRepo = cuponRepo;
        this.pagoRepo = pagoRepo;
    }

    /**
     * Procesa la transacción completa de Check-in conectando Reserva, Cupón y Pago.
     */
    @Transactional
    public Pago procesarCheckInYpago(Long idReserva, String codigoCupon, MetodoPago metodo, Double montoRecibido) {
        
        // Cargar la reserva
        Reserva reserva = reservaRepo.findById(idReserva)
                .orElseThrow(() -> new BusinessRuleException("Reserva no encontrada"));

        // REGLA 1: Solo se puede hacer check-in si la reserva está pendiente
        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new BusinessRuleException("La reserva no está en estado PENDIENTE.");
        }

        // REGLA 2: No se puede procesar antes de la fecha de inicio
        if (LocalDate.now().isBefore(reserva.getFechaInicio())) {
            throw new BusinessRuleException("Aún no es la fecha de inicio de esta reserva.");
        }

        Double totalAPagar = reserva.getSaldoPendiente();
        Cupon cupon = null;

        // Validar y aplicar cupón si el usuario ingresó uno
        if (codigoCupon != null && !codigoCupon.trim().isEmpty()) {
            cupon = cuponRepo.findByCodigo(codigoCupon)
                    .orElseThrow(() -> new BusinessRuleException("Código de cupón inexistente."));

            // REGLA 3: Si se ingresa un cupón, este debe estar ACTIVO
            if (cupon.getEstado() != EstadoCupon.ACTIVO) {
                throw new BusinessRuleException("El cupón ingresado ya fue usado o está inactivo.");
            }

            // Calcular descuento
            Double descuento = totalAPagar * (cupon.getDescuentoPorcentaje() / 100.0);
            totalAPagar = totalAPagar - descuento;
        }

        // REGLA 4 MEJORADA: El monto recibido debe ser mayor o igual al saldo
        if (montoRecibido < totalAPagar) {
            throw new BusinessRuleException("El monto recibido ($" + montoRecibido + 
                ") es insuficiente. El total a pagar es: $" + totalAPagar);
    
        }
        
        // (Opcional) Si quieres que el Pago en la Base de datos guarde el monto real de la tarifa 
        // y no el billete de 1 millón que entregó el cliente, puedes hacer esto:
        // pago.setMontoTotal(totalAPagar); // En lugar de montoRecibido

        // --- A partir de aquí todo es válido. Aplicamos mutaciones de estado ---

        // REGLA 3 (Consecuencia): Cambiar estado del cupón a usado
        if (cupon != null) {
            cupon.setEstado(EstadoCupon.USADO);
            cuponRepo.save(cupon);
        }

        // REGLA 5: La Reserva pasa a CHECK_IN_REALIZADO y la Cabaña a OCUPADA
        reserva.setEstado(EstadoReserva.CHECK_IN_REALIZADO);
        Cabana cabana = reserva.getCabana();
        cabana.setEstado(EstadoCabana.OCUPADA);
        
        reservaRepo.save(reserva);
        cabanaRepo.save(cabana);

        // Finalmente, registrar el Pago
        Pago pago = new Pago();
        pago.setReserva(reserva);
        pago.setMontoTotal(totalAPagar);
        pago.setMetodo(metodo);
        pago.setFechaHora(LocalDateTime.now());
        
        return pagoRepo.save(pago);
    }
}