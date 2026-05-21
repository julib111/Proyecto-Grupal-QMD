package com.edu.uniquindio.co.qmd;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.edu.uniquindio.co.qmd.EnumsEstados.MetodoPago;

@Data
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaHora;
    private Double montoTotal;
    
    @Enumerated(EnumType.STRING)
    private MetodoPago metodo;

    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
}