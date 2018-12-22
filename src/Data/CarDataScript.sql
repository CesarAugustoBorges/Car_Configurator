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


-- tabela de encomendas
CREATE TABLE Encomenda(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
status VARCHAR(45) NOT NULL);

-- tabela de linhas de encomenda
CREATE TABLE LDEncomenda(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
preco FLOAT NOT NULL,
quantidade INT NOT NULL,
idEncomenda int NOT NULL,
FOREIGN KEY(idEncomenda) REFERENCES Encomenda(id)
);


-- tabela para linha de encomenda peça
CREATE TABLE LDEPeça(
idPeca INT NOT NULL,
idLDEncomenda INT NOT NULL,
FOREIGN KEY(idPeca) REFERENCES Peça(id),
FOREIGN KEY(idLDEncomenda) REFERENCES LDEncomenda(id)
);

-- tabela para linha de encomenda pacote
CREATE TABLE LDEPacote(
idpacote INT NOT NULL,
idLDEncomenda INT NOT NULL,
FOREIGN KEY(idpacote) REFERENCES Peça(id),
FOREIGN KEY(idLDEncomenda) REFERENCES LDEncomenda(id)
);

-- tabel de stoks
CREATE TABLE Stock(
qtdisponivel INT NOT NULL,
qtmaxima INT NOT NULL,
idPeça INT NOT NULL,
PRIMARY KEY(idPeça));

-- inserts

INSERT INTO Encomenda
(id,status)
VALUES
(1,"carro fixe"),
(2,"carro feio");

INSERT INTO LDEncomenda
(id,preco,quantidade,idEncomenda)
VALUES
(1,10,1,1);

INSERT INTO LDEPeça
(idPeca,idLDEncomenda)
VALUES
(1,1);

select idPeca from Encomenda as E  inner join LDEncomenda as LDE on E.id = LDE.idEncomenda inner join LDEPeça on LDE.id = LDEPeça.idLDEncomenda where E.id = 1;
		


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