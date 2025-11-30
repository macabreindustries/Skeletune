package org.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PublicacionMediaId implements Serializable {

    @Column(name = "id_publicacion")
    private Integer idPublicacion;

    @Column(name = "id_media")
    private Integer idMedia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicacionMediaId that = (PublicacionMediaId) o;
        return Objects.equals(idPublicacion, that.idPublicacion) &&
               Objects.equals(idMedia, that.idMedia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPublicacion, idMedia);
    }
}
