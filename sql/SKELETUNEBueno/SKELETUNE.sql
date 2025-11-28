-- ============================================================
-- SKELETUNE — SCRIPT FINAL COMPLETO
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
-- 3. VALIDACIONES DE ROLES
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
-- 4. INSTRUMENTOS
-- ============================================================
CREATE TABLE Instrumento (
    id_instrumento INT AUTO_INCREMENT PRIMARY KEY,
    nombre_instrumento VARCHAR(100) NOT NULL UNIQUE,
    tipo VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 5. RELACIÓN USUARIO-INSTRUMENTO (N:M)
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
-- 6. CANCIONES
-- ============================================================
CREATE TABLE Cancion (
    id_cancion INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    artista VARCHAR(100),
    dificultad ENUM('facil','media','dificil') DEFAULT 'media',
    url_audio VARCHAR(512),
    url_partitura VARCHAR(512),
    imagen_url VARCHAR(512),
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_admin INT NOT NULL,
    FOREIGN KEY (id_admin) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 7. LECCIONES
-- ============================================================
CREATE TABLE Leccion (
    id_leccion INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    tipo ENUM('teoria','practica') DEFAULT 'practica',
    nivel ENUM('principiante','intermedio','avanzado') DEFAULT 'principiante',
    id_cancion INT NULL,
    id_video INT NULL,
    FOREIGN KEY (id_cancion) REFERENCES Cancion(id_cancion)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 8. PROGRESO
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
-- 10. ESTADÍSTICAS
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
-- 11. MEDIA
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
-- 13. PUBLICACION_MEDIA
-- ============================================================
CREATE TABLE PublicacionMedia (
    id_publicacion INT NOT NULL,
    id_media INT NOT NULL,
    PRIMARY KEY (id_publicacion,id_media),
    FOREIGN KEY (id_publicacion) REFERENCES Publicacion(id_publicacion)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_media) REFERENCES Media(id_media)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 14. LIKES
-- ============================================================
CREATE TABLE LikePublicacion (
    id_like INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_publicacion INT NOT NULL,
    fecha_like DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_usuario, id_publicacion),
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
-- 16. SEGUIDORES
-- ============================================================
CREATE TABLE Seguidor (
    id_seguidor INT NOT NULL,
    id_seguido INT NOT NULL,
    fecha_seguimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id_seguidor,id_seguido),
    FOREIGN KEY (id_seguidor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_seguido) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 17. MENSAJES PRIVADOS
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
-- 18. HISTORIAS (stories)
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
-- 19. NOTIFICACIONES
-- ============================================================
CREATE TABLE Notificacion (
    id_notificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo ENUM(
        'sistema','validacion_rol','novedad',
        'progreso','logro','mensaje'
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
-- ========================
-- MINIJUEGO MANIA
-- ========================

-- ============================================================
-- 22. CHARTMANIA
-- ============================================================
CREATE TABLE ChartMania (
    id_chart_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_cancion INT NOT NULL,
    dificultad ENUM('facil','media','dificil','experto') DEFAULT 'media',
    speed_multiplier FLOAT DEFAULT 1.0,
    num_pistas TINYINT NOT NULL DEFAULT 4,
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
-- 23. NOTAMANIA
-- ============================================================
CREATE TABLE NotaMania (
    id_nota_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_chart_mania INT NOT NULL,
    tiempo_ms INT NOT NULL,
    carril TINYINT NOT NULL,
    duracion_ms INT DEFAULT 0,
    imagen_url VARCHAR(512),
    tipo ENUM('normal','hold','flick','rest') DEFAULT 'normal',
    FOREIGN KEY (id_chart_mania) REFERENCES ChartMania(id_chart_mania)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_notamania_chart_tiempo
    ON NotaMania(id_chart_mania, tiempo_ms);

-- ============================================================
-- 24. PARTIDAMANIA
-- ============================================================
CREATE TABLE PartidaMania (
    id_partida_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_chart_mania INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    puntaje INT NOT NULL,
    accuracy DECIMAL(5,2) NOT NULL,
    combo_max INT NOT NULL,
    perfects INT DEFAULT 0,
    greats INT DEFAULT 0,
    goods INT DEFAULT 0,
    misses INT DEFAULT 0,
	detalles LONGTEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_chart_mania) REFERENCES ChartMania(id_chart_mania)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 25. FALLOMANIA
-- ============================================================
CREATE TABLE FalloMania (
    id_fallo_mania INT AUTO_INCREMENT PRIMARY KEY,
    id_partida_mania INT NOT NULL,
    tiempo_ms INT NOT NULL,
    tipo ENUM('early','late','miss') DEFAULT 'miss',
    desviacion_ms INT,
    FOREIGN KEY (id_partida_mania) REFERENCES PartidaMania(id_partida_mania)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 26. VIDEO EDUCATIVO
-- ============================================================
CREATE TABLE VideoEducativo (
    id_video INT AUTO_INCREMENT PRIMARY KEY,
    id_profesor INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    url_video VARCHAR(512) NOT NULL,
    thumbnail_url VARCHAR(512),
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_profesor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- FK FINAL: LECCION → VIDEO EDUCATIVO
-- ============================================================
ALTER TABLE Leccion
    ADD CONSTRAINT fk_leccion_video
    FOREIGN KEY (id_video) REFERENCES VideoEducativo(id_video)
        ON UPDATE CASCADE
        ON DELETE SET NULL;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- INSERTS DE PRUEBA
-- ============================================================
INSERT INTO Rol(nombre_rol) VALUES
('Administrador'),
('Profesor'),
('Alumno');

INSERT INTO Usuario(nombre, correo, contrasena, id_rol) VALUES
('Admin', 'admin@skeletune.com', '123', 1),
('Profe', 'profe@skeletune.com', '123', 2),
('Alumno', 'alumno@skeletune.com', '123', 3);

INSERT INTO Instrumento(nombre_instrumento,tipo) VALUES
('Guitarra','Cuerdas'),
('Piano','Teclas');

INSERT INTO Cancion(titulo, artista, id_admin)
VALUES('Ejemplo Song', 'Anon', 1);

INSERT INTO VideoEducativo(id_profesor, titulo, url_video)
VALUES(2, 'Cómo afinar guitarra', 'https://youtube.com/example');

INSERT INTO Leccion(titulo, id_cancion, id_video)
VALUES('Lección 1', 1, 1);
