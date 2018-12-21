DROP DATABASE CarData;

CREATE DATABASE CarData; 
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

-- tabela de Pacotes
CREATE TABLE Pacote(
id INT NOT NULL AUTO_INCREMENT,
preco FLOAT NOT NULL,
descricao VARCHAR(45) NOT NULL,
PRIMARY KEY(id));


-- tabela de Peças
 CREATE TABLE Peça(
 id INT NOT NULL AUTO_INCREMENT,
 categoria VARCHAR(45) NOT NULL,
 idpacote INT ,
 PRIMARY KEY(id),
 FOREIGN KEY(idpacote) REFERENCES Pacote(id));
 
 -- tabela de Peças dependentes
CREATE TABLE PeçasDependentes(
id1 INT NOT  NULL,
iddependente INT NOT NULL,
Primary Key(id1,iddependente));

-- tabela de peças incompativeis
CREATE TABLE PeçasIncompativeis(
id1 INT NOT NULL,
idincompativel INT NOT NULL,
PRIMARY Key(id1,idincompativel));

            
-- tabel de stoks
CREATE TABLE Stock(
qtdisponivel INT NOT NULL,
qtmaxima INT NOT NULL,
idPeça INT NOT NULL,
PRIMARY KEY(idPeça));

-- inserts

Insert Into Stock
(qtdisponivel,qtmaxima,idPeça)
VALUES
(10,12,1),
(20,24,2),
(30,50,4),
(10,500,5);
 
 INSERT Into Pacote
 (id,preco,descricao)
 VALUES
 (1,10,"Pacote Desportivo"),
 (2,5,"Pacote Incompleto");
 
 

 Insert Into Peça
 (categoria,idpacote)
 VALUES
 ("Roda",1), ("Volante",2),("Porta",null),("Vidro",null),("Jante",null),("motor",null),("tubo",null),("pintura",null),("espelho",null),("capo",null);

 
 
 INSERT INTO PeçasDependentes
 (id1,iddependente)
 VALUES
 (1,1),(1,2),(1,3);
 
 
 INSERT INTO PeçasIncompativeis
 (id1,idincompativel)
 VALUES
 (1,4),(1,1);

 
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