package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "VideoEducativo")
public class VideoEducativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_video")
    private Integer idVideo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesor", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario profesor;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_video", nullable = false, length = 512)
    private String urlVideo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_thumbnail_media", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Media thumbnailMedia;

    @CreationTimestamp
    @Column(name = "fecha_subida", updatable = false)
    private LocalDateTime fechaSubida;
}
