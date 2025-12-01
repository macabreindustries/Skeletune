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
@Table(name = "LikePublicacion", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_usuario", "id_publicacion"})
})
public class LikePublicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_like")
    private Integer idLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publicacion", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Publicacion publicacion;

    @CreationTimestamp
    @Column(name = "fecha_like", updatable = false)
    private LocalDateTime fechaLike;
}
