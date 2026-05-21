package com.edu.uniquindio.co.qmd;

import jakarta.persistence.*;
import lombok.Data;
import com.edu.uniquindio.co.qmd.EnumsEstados.EstadoCupon;

@Data
@Entity
@Table(name = "cupones")
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String codigo;
    private Double descuentoPorcentaje;
    
    @Enumerated(EnumType.STRING)
    private EstadoCupon estado;
}