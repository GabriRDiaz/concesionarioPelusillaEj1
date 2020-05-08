# Conexión simple a DB 

Este programa ejecuta las funciones INSERT, SELECT, UPDATE, DELETE sobre una base de datos.

# Info DB:

CREATE DATABASE concesionariopelusilla;

# Script DB:

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `cochos` (
  `id` int(11) NOT NULL,
  `pata` varchar(100) NOT NULL,
  `alimentacion` varchar(100) NOT NULL,
  `numMarcado` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `compradores` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `cochos`
  ADD PRIMARY KEY (`id`,`numMarcado`);

ALTER TABLE `compradores`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `cochos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `compradores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;
