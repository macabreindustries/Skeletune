package org.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PublicacionMedia")
public class PublicacionMedia {

    @EmbeddedId
    private PublicacionMediaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPublicacion")
    @JoinColumn(name = "id_publicacion")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Publicacion publicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMedia")
    @JoinColumn(name = "id_media")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Media media;
}
