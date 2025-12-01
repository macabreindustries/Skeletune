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
public class SeguidorId implements Serializable {

    @Column(name = "id_seguidor")
    private Integer idSeguidor;

    @Column(name = "id_seguido")
    private Integer idSeguido;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeguidorId that = (SeguidorId) o;
        return Objects.equals(idSeguidor, that.idSeguidor) &&
               Objects.equals(idSeguido, that.idSeguido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSeguidor, idSeguido);
    }
}
