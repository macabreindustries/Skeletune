package org.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Seguidor")
public class Seguidor {

    @EmbeddedId
    private SeguidorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSeguidor")
    @JoinColumn(name = "id_seguidor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario seguidor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSeguido")
    @JoinColumn(name = "id_seguido")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario seguido;

    @CreationTimestamp
    @Column(name = "fecha_seguimiento", updatable = false)
    private LocalDateTime fechaSeguimiento;
}
