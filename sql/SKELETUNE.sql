-- ============================================================
-- SKELETUNE — SCRIPT FINAL (estructura original + minijuego mínimo + mini-YouTube)
-- - Mantiene tu estructura original (tablas 1..21) con los ajustes solicitados:
--   * No hay Llamada ni Videollamada.
--   * Se añade módulo mínimo para minijuego tipo osu!mania (4K fijo).
--   * Se añade VideoEducativo (profesores suben videos; lecciones pueden enlazarlos).
-- - Incluye INSERTS de prueba básicos.
-- - Recomendación: hacer BACKUP antes de ejecutar en bases con datos.
-- - Requiere MySQL 5.7+ / 8.0+
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS skeletune;
CREATE DATABASE skeletune CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE skeletune;

-- ============================================================
-- 1. TABLA DE ROLES
-- ============================================================
CREATE TABLE Rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 2. TABLA DE USUARIOS
-- ============================================================
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES Rol(id_rol)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 3. VALIDACIONES DE ROLES (hechas por administradores)
-- ============================================================
CREATE TABLE ValidacionRol (
    id_validacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_validado INT NOT NULL,
    id_admin_validador INT NOT NULL,
    fecha_validacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('pendiente','aprobado','rechazado') DEFAULT 'pendiente',
    FOREIGN KEY (id_usuario_validado) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_admin_validador) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 4. INSTRUMENTOS DISPONIBLES
-- ============================================================
CREATE TABLE Instrumento (
    id_instrumento INT AUTO_INCREMENT PRIMARY KEY,
    nombre_instrumento VARCHAR(100) NOT NULL UNIQUE,
    tipo VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 5. RELACIÓN N:M — USUARIOS E INSTRUMENTOS
-- ============================================================
CREATE TABLE UsuarioInstrumento (
    id_usuario INT NOT NULL,
    id_instrumento INT NOT NULL,
    nivel ENUM('principiante','intermedio','avanzado') DEFAULT 'principiante',
    PRIMARY KEY (id_usuario, id_instrumento),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_instrumento) REFERENCES Instrumento(id_instrumento)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 6. CANCIONES (solo administradores pueden subir) + imagen
-- ============================================================
CREATE TABLE Cancion (
    id_cancion INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    artista VARCHAR(100),
    dificultad ENUM('facil','media','dificil') DEFAULT 'media',
    url_audio VARCHAR(512),
    url_partitura VARCHAR(512),
    imagen_url VARCHAR(512),              -- << NUEVO
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_admin INT NOT NULL,
    FOREIGN KEY (id_admin) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 7. LECCIONES (teoría o práctica)  -- incluye id_video (sin FK aún)
-- ============================================================
CREATE TABLE Leccion (
    id_leccion INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    tipo ENUM('teoria','practica') DEFAULT 'practica',
    nivel ENUM('principiante','intermedio','avanzado') DEFAULT 'principiante',
    id_cancion INT NULL,
    id_video INT NULL, -- se añadirá FK a VideoEducativo más abajo
    FOREIGN KEY (id_cancion) REFERENCES Cancion(id_cancion)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 8. HISTORIAL DE PROGRESO
-- ============================================================
CREATE TABLE Progreso (
    id_progreso INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_leccion INT NULL,
    fecha DATE NOT NULL,
    duracion_minutos INT DEFAULT 0,
    comentario TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_leccion) REFERENCES Leccion(id_leccion)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 9. NOVEDADES
-- ============================================================
CREATE TABLE Novedad (
    id_novedad INT AUTO_INCREMENT PRIMARY KEY,
    id_admin INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    contenido TEXT NOT NULL,
    imagen_url VARCHAR(512),
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    importancia ENUM('alta','media','baja') DEFAULT 'media',
    FOREIGN KEY (id_admin) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 10. ESTADÍSTICAS DEL USUARIO
-- ============================================================
CREATE TABLE EstadisticaUsuario (
    id_estadistica INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_minutos_practica INT DEFAULT 0,
    lecciones_completadas INT DEFAULT 0,
    canciones_aprendidas INT DEFAULT 0,
    racha_dias INT DEFAULT 0,
    nivel_general ENUM('principiante','intermedio','avanzado') DEFAULT 'principiante',
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 11. MEDIA (Fotos, videos, audios)
-- ============================================================
CREATE TABLE Media (
    id_media INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo ENUM('foto','video','audio') NOT NULL,
    url_archivo VARCHAR(512) NOT NULL,
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 12. PUBLICACIONES
-- ============================================================
CREATE TABLE Publicacion (
    id_publicacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    texto TEXT,
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_media_principal INT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_media_principal) REFERENCES Media(id_media)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 13. PUBLICACION_MEDIA (múltiples fotos/videos)
-- ============================================================
CREATE TABLE PublicacionMedia (
    id_publicacion INT NOT NULL,
    id_media INT NOT NULL,
    PRIMARY KEY (id_publicacion, id_media),
    FOREIGN KEY (id_publicacion) REFERENCES Publicacion(id_publicacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_media) REFERENCES Media(id_media)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 14. LIKES DE PUBLICACIONES
-- ============================================================
CREATE TABLE LikePublicacion (
    id_like INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_publicacion INT NOT NULL,
    fecha_like DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (id_usuario, id_publicacion),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_publicacion) REFERENCES Publicacion(id_publicacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 15. COMENTARIOS
-- ============================================================
CREATE TABLE Comentario (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    id_publicacion INT NOT NULL,
    id_usuario INT NOT NULL,
    comentario TEXT NOT NULL,
    fecha_comentario DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_publicacion) REFERENCES Publicacion(id_publicacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 16. SEGUIDORES (followers estilo Instagram)
-- ============================================================
CREATE TABLE Seguidor (
    id_seguidor INT NOT NULL,
    id_seguido INT NOT NULL,
    fecha_seguimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_seguidor, id_seguido),
    FOREIGN KEY (id_seguidor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_seguido) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 17. MENSAJES PRIVADOS (chat)
-- ============================================================
CREATE TABLE Mensaje (
    id_mensaje INT AUTO_INCREMENT PRIMARY KEY,
    id_emisor INT NOT NULL,
    id_receptor INT NOT NULL,
    mensaje TEXT,
    id_media INT NULL,
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    visto BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_emisor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_receptor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_media) REFERENCES Media(id_media)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 18. (REMOVED) Llamadas -> NO SE CREA (según tu petición)
-- 19. (REMOVED) Videollamadas -> NO SE CREA (según tu petición)
-- ============================================================

-- ============================================================
-- 20. HISTORIAS (Stories 24h)
-- ============================================================
CREATE TABLE Historia (
    id_historia INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_media INT NOT NULL,
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    expira_en DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_media) REFERENCES Media(id_media)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 21. NOTIFICACIONES POR USUARIO
-- ============================================================
CREATE TABLE Notificacion (
    id_notificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo ENUM(
        'sistema',
        'validacion_rol',
        'novedad',
        'progreso',
        'logro',
        'mensaje'
    ) DEFAULT 'sistema',
    titulo VARCHAR(150) NOT NULL,
    mensaje TEXT NOT NULL,
    id_referencia INT NULL,
    tabla_referencia VARCHAR(50) NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    leido BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- ===========================
-- MINIJUEGOS (lo mínimo necesario, 4K fijo)
-- ============================
-- Usan las canciones de la tabla Cancion. Solo guardamos partidas y fallos.
-- ============================================================

-- ============================================================
-- 22. ChartMania (mapa para un minijuego tipo osu!mania - 4 pistas fijas)
-- ============================================================
CREATE TABLE ChartMania (
    id_chart_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_cancion INT NOT NULL,
    dificultad ENUM('facil','media','dificil','experto') DEFAULT 'media',
    speed_multiplier FLOAT DEFAULT 1.0,
    num_pistas TINYINT NOT NULL DEFAULT 4, -- 4K fijo
    created_by INT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cancion) REFERENCES Cancion(id_cancion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 23. NotaMania (notas individuales; pueden tener imagen para estilo Songsterr)
-- ============================================================
CREATE TABLE NotaMania (
    id_nota_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_chart_mania INT NOT NULL,
    tiempo_ms INT NOT NULL,
    carril TINYINT NOT NULL,                -- columna/lane (1..4)
    duracion_ms INT DEFAULT 0,              -- 0 = tap, >0 = hold
    imagen_url VARCHAR(512) NULL,           -- opcional: ruta a imagen (p. ej. /mnt/data/...)
    tipo ENUM('normal','hold','flick','rest') DEFAULT 'normal',
    FOREIGN KEY (id_chart_mania) REFERENCES ChartMania(id_chart_mania)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_notamania_chart_tiempo ON NotaMania(id_chart_mania, tiempo_ms);

-- ============================================================
-- 24. PartidaMania (resultado de una jugada de ChartMania)
-- ============================================================
CREATE TABLE PartidaMania (
    id_partida_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_chart_mania INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    puntaje INT NOT NULL,
    accuracy DECIMAL(5,2) NOT NULL,      -- porcentaje 0.00 - 100.00
    combo_max INT NOT NULL,
    perfects INT DEFAULT 0,
    greats INT DEFAULT 0,
    goods INT DEFAULT 0,
    misses INT DEFAULT 0,
    detalles JSON NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_chart_mania) REFERENCES ChartMania(id_chart_mania)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 25. FalloMania (registro de fallos / errores de timing por partida)
-- ============================================================
CREATE TABLE FalloMania (
    id_fallo_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_partida_mania INT NOT NULL,
    tiempo_ms INT NOT NULL,
    tipo ENUM('early','late','miss') DEFAULT 'miss',
    desviacion_ms INT NULL,
    FOREIGN KEY (id_partida_mania) REFERENCES PartidaMania(id_partida_mania)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 26. VideoEducativo (mini-YouTube: profesores suben videos; alumnos ven)
-- ============================================================
CREATE TABLE VideoEducativo (
    id_video INT AUTO_INCREMENT PRIMARY KEY,
    id_profesor INT NOT NULL,             -- debe ser un usuario con rol Profesor/Admin
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    url_video VARCHAR(512) NOT NULL,      -- puede ser enlace (YouTube) o ruta local (mp4)
    thumbnail_url VARCHAR(512) NULL,
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_profesor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Conectar Leccion.id_video a VideoEducativo (FK) ahora que existe VideoEducativo
-- ============================================================
ALTER TABLE Leccion
  ADD CONSTRAINT fk_leccion_video FOREIGN KEY (id_video) REFERENCES VideoEducativo(id_video)
    ON UPDATE CASCADE
    ON DELETE SET NULL;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- INSERTS DE PRUEBA BÁSICOS
-- ============================================================

-- 1) Roles
INSERT INTO Rol (nombre_rol, descripcion) VALUES
('Administrador', 'Control total del sistema y subida de canciones.'),
('Profesor', 'Enseñanza.'),
('Usuario', 'Aprendiz.');

-- 2) Usuarios (admin + profesor + demo)
INSERT INTO Usuario (nombre, correo, contrasena, id_rol) VALUES
('Administrador Principal', 'admin@skeletune.com', 'hashed_password', (SELECT id_rol FROM Rol WHERE nombre_rol='Administrador' LIMIT 1)),
('Profesor Demo', 'profesor@skeletune.com', 'hashed_password', (SELECT id_rol FROM Rol WHERE nombre_rol='Profesor' LIMIT 1)),
('Usuario Demo', 'user@skeletune.com', 'hashed_password', (SELECT id_rol FROM Rol WHERE nombre_rol='Usuario' LIMIT 1));

-- 3) Media (uso de archivo subido; usando la ruta de archivo que subiste en la sesión)
-- developer note: using uploaded file path as URL per your dev instruction
INSERT INTO Media (id_usuario, tipo, url_archivo, descripcion) VALUES
((SELECT id_usuario FROM Usuario WHERE correo='admin@skeletune.com' LIMIT 1), 'foto', '/mnt/data/50320cfb-8101-4d1a-86f8-97bcaae19aee.png', 'Thumbnail demo uploaded by admin');

-- 4) Canciones (ambas canciones serán usadas por los minijuegos)
INSERT INTO Cancion (titulo, artista, dificultad, url_audio, url_partitura, imagen_url, id_admin) VALUES
('Blinding Lights', 'The Weeknd', 'media', '/audio/The Weeknd - Blinding Lights (Official Audio).mp3', '/scores/midnight_dreams.pdf', 'https://st1.uvnimg.com/31/a6/5af29f294a26805fb7bb5fb78852/17-the-weeknd.jpg',
 (SELECT id_usuario FROM Usuario WHERE correo='admin@skeletune.com' LIMIT 1)),
('Ocean Waves', 'Frank Ocean', 'facil', '/audio/ocean_waves.mp3', '/scores/ocean_waves.pdf', '/mnt/data/50320cfb-8101-4d1a-86f8-97bcaae19aee.png',
 (SELECT id_usuario FROM Usuario WHERE correo='admin@skeletune.com' LIMIT 1));


-- 5) Charts mínimos (uno por canción, 4 pistas fijas)
INSERT INTO ChartMania (id_cancion, dificultad, speed_multiplier, num_pistas, created_by) VALUES
((SELECT id_cancion FROM Cancion WHERE titulo='Blinding Lights' LIMIT 1), 'media', 1.0, 4, (SELECT id_usuario FROM Usuario WHERE correo='profesor@skeletune.com' LIMIT 1));


-- 8) Fallos asociados a la partida (si los hubo)
INSERT INTO FalloMania (id_partida_mania, tiempo_ms, tipo, desviacion_ms) VALUES
((SELECT id_partida_mania FROM PartidaMania ORDER BY id_partida_mania DESC LIMIT 1), 56000, 'miss', NULL),
((SELECT id_partida_mania FROM PartidaMania ORDER BY id_partida_mania DESC LIMIT 1), 12345, 'late', 42);

-- 9) Video educativo demo (profesor puede subir mp4 o enlazar YT; using uploaded path as thumbnail/sample)
INSERT INTO VideoEducativo (id_profesor, titulo, descripcion, url_video, thumbnail_url) VALUES
((SELECT id_usuario FROM Usuario WHERE correo='profesor@skeletune.com' LIMIT 1),
 'Introducción a Midnight Dreams (Clase demo)',
 'Clase demo corta del profesor sobre Midnight Dreams',
 '/video_uploads/midnight_dreams_demo.mp4',
 '/mnt/data/50320cfb-8101-4d1a-86f8-97bcaae19aee.png');

-- 10) Asociar una lección a un video (ejemplo)
UPDATE Leccion SET id_video = (SELECT id_video FROM VideoEducativo WHERE titulo LIKE 'Introducción a Midnight Dreams (Clase demo)' LIMIT 1)
WHERE id_leccion IS NULL LIMIT 1;

