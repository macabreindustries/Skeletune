package org.example.demo.repository;

import org.example.demo.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
    
    List<Cancion> findByArtistaContainingIgnoreCase(String artista);
    
    List<Cancion> findByDificultad(Cancion.Dificultad dificultad);
    
    List<Cancion> findByUrlAudio(String urlAudio);
    
    List<Cancion> findByUrlPartitura(String urlPartitura);
}