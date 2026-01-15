package org.example.demo.repository;

import org.example.demo.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Integer> {

    Optional<Cancion> findByTitulo(String titulo);

    void deleteByTitulo(String titulo);

    @Query("SELECT DISTINCT c.titulo FROM Cancion c")
    List<String> findAllTitulos();

    @Query("SELECT DISTINCT c.artista FROM Cancion c")
    List<String> findAllArtistas();

    @Query("SELECT DISTINCT c.dificultad FROM Cancion c")
    List<Cancion.Dificultad> findAllDificultades();

    @Query("SELECT DISTINCT c.urlAudio FROM Cancion c")
    List<String> findAllUrlAudios();

    @Query("SELECT DISTINCT c.urlPartitura FROM Cancion c")
    List<String> findAllUrlPartituras();

    @Query("SELECT DISTINCT c.imagenUrl FROM Cancion c") // Nuevo método
    List<String> findAllImagenUrls();
    
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
    
    List<Cancion> findByArtistaContainingIgnoreCase(String artista);
    
    List<Cancion> findByDificultad(Cancion.Dificultad dificultad);
    
    List<Cancion> findByUrlAudio(String urlAudio);
    
    List<Cancion> findByUrlPartitura(String urlPartitura);

    List<Cancion> findByImagenUrl(String imagenUrl); // Nuevo método

    // Obtener una canción aleatoria
    @Query(value = "SELECT * FROM cancion ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Cancion> findRandom();

    // Incrementar contadores de forma atómica
    @Modifying
    @Query("UPDATE Cancion c SET c.likesCount = c.likesCount + 1 WHERE c.idCancion = :id")
    void incrementLikes(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE Cancion c SET c.viewsCount = c.viewsCount + 1 WHERE c.idCancion = :id")
    void incrementViews(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE Cancion c SET c.swipesCount = c.swipesCount + 1 WHERE c.idCancion = :id")
    void incrementSwipes(@Param("id") Integer id);
}
