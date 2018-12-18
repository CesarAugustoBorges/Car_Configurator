-- CREATE DATABASE CarData; 
Use CarData;

CREATE TABLE `Cliente` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `nif` VARCHAR(45) NOT NULL,
 `nome` VARCHAR(45) NOT NULL,
 PRIMARY KEY (`id`));

CREATE TABLE Funcionario(
 id INT NOT NULL auto_increment,
 nif VARCHAR(45) NOT NULL,
 nome VARCHAR(45) NOT NULL,
 tipo VARCHAR(45) NOT NULL,
 passe VARCHAR(45) NOT NULL,
 PRIMARY KEY(id));
	
 
     

 
 Insert Into Cliente 
	(nif,nome)
    VALUES
    ("123456789","André Guilherme"),
    ("123456788","César Augusto"),
    ("123456777","Mini J"),
    ("123456666","Discipulo");
-- select * from Cliente;


Insert Into Funcionario
(nif,nome,tipo,passe)
VALUES
("123456789","Vedeta","Gestor","vedeta"),
("123456788","Draven","Gestor","axes"),
("123456777","Lux","Gestor","narnia"),
("123456666","Defenido","Admin","bolos");
 select * from Funcionario;
