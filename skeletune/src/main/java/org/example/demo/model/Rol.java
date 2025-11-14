package org.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer id;

    @Column(name = "nombre_rol", nullable = false, unique = true)
    private String nombre;

    @Column
    private String descripcion;
}
