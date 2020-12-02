CREATE DATABASE IF NOT EXISTS practica;

ALTER DATABASE practica
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE practica;

CREATE TABLE IF NOT EXISTS direcciones (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ciudad VARCHAR(30),
  calle VARCHAR(30),
  numero INT(4),
  id_cliente INT(4),
  INDEX(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS clientes (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(30),
  estado INT,
  direccion_id INT(4) UNSIGNED NOT NULL,
  FOREIGN KEY (direccion_id) REFERENCES direcciones(id),
  INDEX(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS visitas (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  fecha DATE,
  importe FLOAT,
  id_cliente INT(4),
  estado BOOLEAN,
  INDEX(id)
) engine=InnoDB;

