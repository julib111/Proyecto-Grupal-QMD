package com.edu.uniquindio.co.qmd;

import jakarta.persistence.*;
import lombok.Data;
import com.edu.uniquindio.co.qmd.EnumsEstados.EstadoCabana;

@Data
@Entity
@Table(name = "cabanas")
public class Cabana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer capacidad;
    private Double precioNoche;

    @Enumerated(EnumType.STRING)
    private EstadoCabana estado;
}
