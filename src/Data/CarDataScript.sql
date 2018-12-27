DROP DATABASE IF EXISTS CarData;
CREATE DATABASE CarData; 
Use CarData;




-- tabela de clientes
CREATE TABLE `Cliente` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `nif` VARCHAR(45) NOT NULL,
 `nome` VARCHAR(45) NOT NULL,
 PRIMARY KEY (`id`));

-- tabela de encomendas
CREATE TABLE Encomenda(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
estado VARCHAR(45) NOT NULL,
descricao VARCHAR(45) NOT NULL,
idCliente INT NOT NULL,
FOREIGN KEY (idCliente) references Cliente(id));

-- tabela de encomendas de um cliente
CREATE TABLE EncomendaCliente(
idCliente INT NOT NULL,
idEncomenda INT NOT NULL,
Primary Key(idCliente,idEncomenda),
FOREIGN Key (idCliente) REFERENCES Cliente(id),
FOREIGN KEY(idEncomenda) REFERENCES Encomenda(id));

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
id INT NOT NULL ,
preco FLOAT NOT NULL,
descricao VARCHAR(45) NOT NULL,
PRIMARY KEY(id));


-- tabela de Peças
 CREATE TABLE Peça(
 id INT NOT NULL AUTO_INCREMENT,
 preco FLOAT NOT NULL,
 categoria VARCHAR(45) NOT NULL,
 descricao VARCHAR(45) NOT NULL,
 PRIMARY KEY(id));
 
 
 -- tabela de Peças do Pacote
 CREATE TABLE PeçaDoPacote(
 idPacote INT NOT NULL,
 idPeca INT NOT NULL,
 primary key(idPacote,idPeca),
 FOREIGN KEY (idPacote) REFERENCES Pacote(id),
 FOREIGN KEY (idPeca) references Peça(id));
 
 
 -- tabela de Peças dependentes
CREATE TABLE PeçasDependentes(
id1 INT NOT  NULL,
iddependente INT NOT NULL,
Primary Key(id1,iddependente),
FOREIGN KEY (id1) references Peça(id));

-- tabela de peças incompativeis
CREATE TABLE PeçasIncompativeis(
id1 INT NOT NULL,
idincompativel INT NOT NULL,
PRIMARY Key(id1,idincompativel),
FOREIGN KEY (id1) references Peça(id));

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
PRIMARY KEY(idPeca,idLDEncomenda),
FOREIGN KEY(idPeca) REFERENCES Peça(id),
FOREIGN KEY(idLDEncomenda) REFERENCES LDEncomenda(id)
);

-- tabela para linha de encomenda pacote
CREATE TABLE LDEPacote(
idpacote INT NOT NULL,
idLDEncomenda INT NOT NULL,
PRIMARY KEY(idPacote,idLDEncomenda),
FOREIGN KEY(idpacote) REFERENCES Pacote(id),
FOREIGN KEY(idLDEncomenda) REFERENCES LDEncomenda(id)
);

-- tabel de stoks
CREATE TABLE Stock(
qtdisponivel INT NOT NULL,
qtmaxima INT NOT NULL,
idPeça INT NOT NULL,
PRIMARY KEY(idPeça),
FOREIGN Key(idPeça) references Peça(id));




-- POVOVAMENTO DAS TABELAS
INSERT Into Pacote
 (id,preco,descricao)
 VALUES
 (1,10,"Pacote Desportivo"),
 (2,5,"Pacote Incompleto");
 	


 Insert Into Peça
 (descricao,categoria,preco)
 VALUES
 ("Roda","a",10), ("Volante","ro",20),("Porta","p",30),("Vidro","v",4),("Jante","j",4),("motor","m",22),("tubo","t",10.2),("pintura","p,",123.2),("espelho","e",123),("capo","c",213);

Insert Into Stock
(qtdisponivel,qtmaxima,idPeça)
VALUES
(10,12,1),
(20,24,2),
(30,50,4),
(10,500,5);
 

select * from Encomenda;
  
 Insert Into Cliente 
(nif,nome)
VALUES
    ("123456789","André Guilherme"),
    ("123456788","César Augusto"),
    ("123456777","Mini J"),
    ("123456666","Discipulo");

INSERT INTO Encomenda
(id,descricao,estado,idCliente)
VALUES
(1,"carro fixe","por validar",1),
(2,"carro feio","por validar",1);

INSERT Into EncomendaCliente
(idCliente,idEncomenda)
VALUES
(1,1),
(2,2);


Insert Into Funcionario
(nif,nome,tipo,passe)
VALUES
("123456789","Vedeta","Gestor","vedeta"),
("123456788","Draven","Gestor","axes"),
("123456777","Lux","Gestor","narnia"),
("123456666","Defenido","Admin","bolos");
 

 
 INSERT INTO PeçasDependentes
 (id1,iddependente)
 VALUES
 (1,1),(1,2),(1,3);
 
 
 
 INSERT INTO PeçasIncompativeis
 (id1,idincompativel)
 VALUES
 (1,4),(1,1);
 
 
INSERT INTO LDEncomenda
(id,preco,quantidade,idEncomenda)
VALUES
(1,10,1,1),
(2,20,3,1);

INSERT INTO LDEPeça
(idPeca,idLDEncomenda)
VALUES
(1,1);

INSERT INTO LDEPacote
(idPacote,idLDEncomenda)
VALUES
(1,1),(1,2);


INSERT INTO PeçaDoPacote
(idPacote,idPeca)
VALUES
(1,1),(1,2),(1,3),(1,4),
(2,1),(2,2);
