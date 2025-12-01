package org.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Publicacion")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Integer idPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @Column(name = "texto", columnDefinition = "TEXT")
    private String texto;

    @CreationTimestamp
    @Column(name = "fecha_publicacion", updatable = false)
    private LocalDateTime fechaPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_media_principal")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Media mediaPrincipal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PublicacionMedia",
            joinColumns = @JoinColumn(name = "id_publicacion"),
            inverseJoinColumns = @JoinColumn(name = "id_media")
    )
    private Set<Media> mediaAdjuntos = new HashSet<>();
}
