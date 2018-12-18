-- CREATE DATABASE CarData; 
Use CarData;

CREATE TABLE `Cliente` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `nif` VARCHAR(45) NOT NULL,
 `nome` VARCHAR(45) NOT NULL,
 PRIMARY KEY (`id`));
 
 
 
 Insert Into Cliente 
	(nif,nome)
    VALUES
    ("123456789","André Guilherme"),
    ("123456788","César Augusto"),
    ("123456777","Mini J"),
    ("123456666","Discipulo");
    
-- select * from Cliente;