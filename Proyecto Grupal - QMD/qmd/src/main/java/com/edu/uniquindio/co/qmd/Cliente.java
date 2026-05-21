package com.edu.uniquindio.co.qmd;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String identificacion;
    
    @Column(nullable = false)
    private String nombre;
    
    private String email;
    private String telefono;
    
    private Boolean activo = true; // Para el borrado lógico o estado
}