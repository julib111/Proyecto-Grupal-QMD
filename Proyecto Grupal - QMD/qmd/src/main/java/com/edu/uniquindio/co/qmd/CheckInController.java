package com.edu.uniquindio.co.qmd;
import com.edu.uniquindio.co.qmd.EnumsEstados.MetodoPago;

import lombok.Data;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    private final CheckInService checkInService;
    private final ReservaRepository reservaRepo; // Inyectamos el repositorio
    private final CuponRepository cuponRepo;     // Inyectamos el repositorio

    public CheckInController(CheckInService checkInService, ReservaRepository reservaRepo, CuponRepository cuponRepo) {
        this.checkInService = checkInService;
        this.reservaRepo = reservaRepo;
        this.cuponRepo = cuponRepo;
    }

    // ENDPOINT ESTRELLA (Ya lo tenías)
    @PostMapping
    public ResponseEntity<?> registrarCheckIn(@RequestBody CheckInRequest request) {
        try {
            Pago pago = checkInService.procesarCheckInYpago(
                request.getIdReserva(), 
                request.getCodigoCupon(), 
                request.getMetodoPago(), 
                request.getMontoRecibido()
            );
            return ResponseEntity.ok(pago);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // NUEVO: Endpoint para buscar la reserva real en la BD
    @GetMapping("/reserva/{id}")
    public ResponseEntity<?> buscarReservaPorId(@PathVariable Long id) {
        var reservaOpt = reservaRepo.findById(id);
        
        if (reservaOpt.isPresent()) {
            var r = reservaOpt.get();
            Map<String, Object> data = new HashMap<>();
            data.put("id", r.getId());
            data.put("saldoPendiente", r.getSaldoPendiente());
            data.put("estado", r.getEstado());
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.badRequest().body("Reserva no encontrada en el sistema.");
        }
    }

    // NUEVO: Endpoint para validar el cupón en la BD antes de pagar
    @GetMapping("/cupon/{codigo}")
    public ResponseEntity<?> consultarCupon(@PathVariable String codigo) {
        var cuponOpt = cuponRepo.findByCodigo(codigo);
        
        if (cuponOpt.isPresent()) {
            var c = cuponOpt.get();
            Map<String, Object> data = new HashMap<>();
            data.put("codigo", c.getCodigo());
            data.put("descuentoPorcentaje", c.getDescuentoPorcentaje());
            data.put("estado", c.getEstado());
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.badRequest().body("El código de cupón no existe.");
        }
    }

    // Objeto de transferencia (DTO) para recibir los datos del formulario JSON
@Data
static class CheckInRequest {
    private Long idReserva;
    private String codigoCupon;
    private MetodoPago metodoPago;
    private Double montoRecibido;
}
}

