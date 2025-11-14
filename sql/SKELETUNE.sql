-- ============================================================
-- BASE DE DATOS: skeletune
-- Descripción: Plataforma tipo Yousician + Instagram
-- ============================================================

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
);

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
);

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
);

-- ============================================================
-- 4. INSTRUMENTOS DISPONIBLES
-- ============================================================
CREATE TABLE Instrumento (
    id_instrumento INT AUTO_INCREMENT PRIMARY KEY,
    nombre_instrumento VARCHAR(100) NOT NULL UNIQUE,
    tipo VARCHAR(100)
);

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
);

-- ============================================================
-- 6. CANCIONES (solo administradores pueden subir)
-- ============================================================
CREATE TABLE Cancion (
    id_cancion INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    artista VARCHAR(100),
    dificultad ENUM('facil','media','dificil') DEFAULT 'media',
    url_audio VARCHAR(255),
    url_partitura VARCHAR(255),
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_admin INT NOT NULL,
    FOREIGN KEY (id_admin) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- ============================================================
-- 7. LECCIONES (teoría o práctica)
-- ============================================================
CREATE TABLE Leccion (
    id_leccion INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    tipo ENUM('teoria','practica') DEFAULT 'practica',
    nivel ENUM('principiante','intermedio','avanzado') DEFAULT 'principiante',
    id_cancion INT NULL,
    FOREIGN KEY (id_cancion) REFERENCES Cancion(id_cancion)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

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
);

-- ============================================================
-- 9. NOVEDADES
-- ============================================================
CREATE TABLE Novedad (
    id_novedad INT AUTO_INCREMENT PRIMARY KEY,
    id_admin INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    contenido TEXT NOT NULL,
    imagen_url VARCHAR(255),
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    importancia ENUM('alta','media','baja') DEFAULT 'media',
    FOREIGN KEY (id_admin) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

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
);





-- ============================================================
-- NUEVAS TABLAS INSTAGRAM + MENSAJERÍA
-- ============================================================

-- ============================================================
-- 11. MEDIA (Fotos, videos, audios)
-- ============================================================
CREATE TABLE Media (
    id_media INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo ENUM('foto','video','audio') NOT NULL,
    url_archivo VARCHAR(255) NOT NULL,
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

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
);

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
);

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
);

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
);

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
);

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
);

-- ============================================================
-- 18. LLAMADAS (voz)
-- ============================================================
CREATE TABLE Llamada (
    id_llamada INT AUTO_INCREMENT PRIMARY KEY,
    id_emisor INT NOT NULL,
    id_receptor INT NOT NULL,
    tipo ENUM('voz') NOT NULL DEFAULT 'voz',
    fecha_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_fin DATETIME NULL,
    estado ENUM('perdida','contestada','cancelada') DEFAULT 'perdida',
    FOREIGN KEY (id_emisor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_receptor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- ============================================================
-- 19. VIDEOLLAMADAS
-- ============================================================
CREATE TABLE Videollamada (
    id_videollamada INT AUTO_INCREMENT PRIMARY KEY,
    id_emisor INT NOT NULL,
    id_receptor INT NOT NULL,
    fecha_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_fin DATETIME NULL,
    estado ENUM('perdida','contestada','cancelada') DEFAULT 'perdida',
    FOREIGN KEY (id_emisor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (id_receptor) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

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
);
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
    id_referencia INT NULL,          -- id opcional de algo relacionado (cancion, leccion, novedad, etc)
    tabla_referencia VARCHAR(50) NULL, -- Cancion, Leccion, Novedad, Usuario...
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    leido BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- 1. Insertar un Rol
INSERT INTO Rol (id_rol, nombre_rol, descripcion) 
VALUES (1, 'Administrador', 'Control total del sistema y subida de canciones.');
INSERT INTO Rol (id_rol, nombre_rol, descripcion) 
VALUES (2, 'Profesor', 'enseñanza.');
INSERT INTO Rol (id_rol, nombre_rol, descripcion) 
VALUES (3, 'usuario', 'aprendiz.');
-- 2. Insertar el Usuario (Admin) que necesitamos en la tabla Cancion
INSERT INTO Usuario (id_usuario, nombre, correo, contrasena, id_rol) 
VALUES (1, 'Administrador Principal', 'admin@skeletune.com', 'hashed_password', 1);
select * from cancion;