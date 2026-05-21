package com.edu.uniquindio.co.qmd;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import com.edu.uniquindio.co.qmd.EnumsEstados.EstadoReserva;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double saldoPendiente;
    
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "cabana_id")
    private Cabana cabana;

    @ManyToOne 
    @JoinColumn(name = "cliente_id") 
    private Cliente cliente;
}