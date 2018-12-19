-- CREATE DATABASE CarData; 
Use CarData;

-- tabela de clientes
CREATE TABLE `Cliente` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `nif` VARCHAR(45) NOT NULL,
 `nome` VARCHAR(45) NOT NULL,
 PRIMARY KEY (`id`));

-- tabela de funcionarios
CREATE TABLE Funcionario(
 id INT NOT NULL auto_increment,
 nif VARCHAR(45) NOT NULL,
 nome VARCHAR(45) NOT NULL,
 tipo VARCHAR(45) NOT NULL,
 passe VARCHAR(45) NOT NULL,
 PRIMARY KEY(id));
	
-- tabela de Peças
 CREATE TABLE Peça(
 id INT NOT NULL AUTO_INCREMENT,
 categoria VARCHAR(45) NOT NULL,
 PRIMARY KEY(id));
 
 -- tabela de Peças dependentes
CREATE TABLE PeçasDependentes(
id1 INT NOT  NULL,
id2 INT NOT NULL,
Primary Key(id1,id2));

-- tabela de peças incompativeis
CREATE TABLE PeçasIncompativeis(
id1 INT NOT NULL,
id2 INT NOT NULL,
PRIMARY Key(id1,id2));

-- tabel de stoks
CREATE TABLE Stock(
qtdisponivel INT NOT NULL,
qtmaxima INT NOT NULL,
idPeça INT NOT NULL,
PRIMARY KEY(idPeça));



Insert Into Stock
(qtdisponivel,qtmaxima,idPeça)
VALUES
(10,12,1),
(20,24,2),
(30,50,4),
(10,500,5);
 
 Insert Into Peça
 (categoria)
 VALUES
 ("Roda"), ("Volante"),("Porta"),("Vidro"),("Jante"),("motor"),("tubo"),("pintura"),("espelho"),("capo");


 
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
