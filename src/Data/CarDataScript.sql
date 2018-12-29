DROP DATABASE IF EXISTS CarData;
CREATE DATABASE CarData; 
Use CarData;

-- tabela de clientes
CREATE TABLE `Cliente` (
 `id` INT NOT NULL,
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


-- tabela de funcionarios
CREATE TABLE Funcionario(
 id INT NOT NULL,
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
 (2,5,"Pacote Caroxa"),
 (3,20,"Pacote M2"),
 (4,24,"Pacote luxo");
 	


 Insert Into Peça
 (categoria,descricao,preco)
 VALUES
 ("Roda","roda desportiva",10),	-- 1
 ("Volante","volante desportivo",20), -- 2
 ("Porta","porta desportiva",30), -- 3
 ("Vidro","vidro da frente",4), -- 4
 ("Vidro","vidro da frente escurecido",20), -- 5
 ("Motor","motor 1000",2000),	-- 6
 ("Vidro","vidro de tras",10.2), -- 7
 ("Vidro","vidro de tras escurecido",20.2), -- 8
 ("Pintura","vermelhor,",123.2), -- 9
 ("Vidro","retrovisres",123), -- 10
 ("Pintura","azul,",123.2),	-- 11
 ("Pintura","preto,",123.2),	-- 12
 ("Pintura","branco,",123.2),	-- 13
 ("Roda","roda casual",10),	-- 14
 ("Volante","volante casual",20),	-- 15
 ("Porta","porta casual",30),	-- 16
 ("Motor","motor 500",1000),	-- 17
 ("Motor","motor 200",500),			-- 18
  ("Roda","roda caroxa",10),			-- 19
 ("Volante","volante caroxa",20),		-- 20
 ("Porta","porta caroxa",30),			-- 21
 ("Extra","radio",30),
 ("Extra","bluetooth",30),
 ("Extra","gps",60),
 ("Extra","ar condicionado",80)
 ;


Insert Into Stock
(qtdisponivel,qtmaxima,idPeça)
VALUES
(10,12,1),
(20,24,2),
(30,50,3),
(30,50,4),
(10,500,5),
(0,50,6),
(30,50,7),
(7,50,8),
(10,50,9),
(30,50,10),
(30,50,11),
(0,50,12),
(0,10,13),
(10,50,14),
(30,50,15),
(30,10,16),
(0,50,17),
(5,30,18),
(0,50,19),
(30,40,20),
(30,50,21),
(50,50,22),
(50,50,23),
(50,50,24),
(50,50,25);
 
  
 Insert Into Cliente 
(id,nif,nome)
VALUES
(1,"123456789","André Guilherme"),
(2,"123456788","César Augusto"),
(3,"123456777","Mini J"),
(4,"123456666","Discipulo"),
(5,"123589269","Vegeta"),
(6,"432156777","Joao Vicente"),
(7,"123121237","Maria Joao"),
(8,"123412154","Mariana Silva"),
(9,"123456722","Son goku"),
(10,"123432133","Diogo Melo"),
(11,"120000722","Samuel Melo"),
(12,"883458877","Jose Valente"),
(13,"083111177","Francisco Cunha"),
(14,"322228877","Jose Silva"),
(15,"744458833","Jose Cunha");

INSERT INTO Encomenda
(id,estado,descricao,idCliente)
VALUES
(1,"Em espera","Encomenda topo de gama",1),
(2,"Em espera","Encomenda carro de luxo",1),
(3,"Em espera","Encomenda desportiva",2),
(4,"Em espera","Encomenda caroxa",2),
(5,"Em espera","Encomenda desportiva",3),
(6,"Em espera","Encomenda topo de gama",9),
(7,"Em espera","Encomenda topo de gama",9),
(8,"Em espera","Encomenda topo de gama",9),
(9,"Em espera","Encomenda topo de gama",9),
(10,"Em espera","Encomenda topo de gama",9),
(11,"Valida","Encomenda caroxa",9),
(12,"Valida","Encomenda topo de gama",7),
(13,"Valida","Encomenda caroxa",15),
(14,"Valida","Encomenda caroxa",11),
(15,"Em espera","Encomenda desportiva",6),
(16,"Valida","Encomenda desportiva",5),
(17,"Em espera","Encomenda topo de gama",12),
(18,"Em espera","Encomenda topo de gama",12)
;



Insert Into Funcionario
(id,nif,nome,tipo,passe)
VALUES
(1,"123456789","Guilherme","Gestor","vedeta"),
(2,"123456788","Draven","Gestor","axes"),
(3,"123456777","Lux","Gestor","narnia"),
(4,"123456666","A coisa","Admin","bolos"),
(5,"123456789","Joao","Funcionario","aaa"),
(6,"123456789","Zeze","Funcionario","qwer"),
(7,"123456789","Maria","Funcionario","naosei"),
(8,"124446789","David","Funcionario","11111"),
(9,"123426722","Gonçalo","Funcionario","passe"),
(10,"333456449","Tiago","Funcionario","1234"),
(11,"12006666","chefe","Admin","eumando"),
(12,"123006666","jesus","Admin","aleluia"),
(13,"123006709","Susana","Gestor","naosei"),
(14,"120036788","Mariana","Gestor","palavra"),
(15,"144426777","Lulu","Gestor","gatinhos"),
(16,"100006789","Joao","Funcionario","passe"),
(17,"123432547","Manel","Funcionario","mamas"),
(18,"123389889","Ze","Funcionario","zezeze"),
(19,"124000789","Carlos","Funcionario","amarelo"),
(20,"003421122","Catarina","Funcionario","cavalo"),
(21,"332200449","Colera","Funcionario","mosca")
;
 

 
 INSERT INTO PeçasDependentes
 (id1,iddependente)
 VALUES
 (1,1),(1,2),(1,3),(2,3),
 (14,14),(14,15),(15,16),(14,16),
 (19,19),(19,20),(19,21),(20,21)
 ;
 
 
 
 INSERT INTO PeçasIncompativeis
 (id1,idincompativel)
 VALUES
 (1,14),(1,19),
 (2,15),(2,20),
 (3,16),(3,21),
 (9,11),(9,12),(9,13),(11,13),(11,12),(12,13)
 ,(4,5),(5,4)
 ,(7,8),(6,7)
 ,(17,18),(6,17),(6,18)
 ;
 
 
INSERT INTO LDEncomenda
(id,preco,quantidade,idEncomenda)
VALUES
(1,101,1,1),
(2,280,3,2),
(3,110,1,3),
(4,10,1,4),
(5,120,1,4),
(6,70,1,4),
(7,130,1,5),
(8,10,1,5),
(9,25,1,5),
(10,33,1,6),
(11,10,1,7),
(12,344,1,8),
(13,11,1,9),
(14,34,1,10),
(15,4,1,11),
(16,34,1,12),
(17,34,1,13),
(18,41,1,14),
(19,34,1,15),
(20,22,1,16),
(21,400,1,17),
(22,200,1,17),
(23,400,1,18),
(24,200,1,18)
;

INSERT INTO LDEPeça
(idPeca,idLDEncomenda)
VALUES
(1,4),
(2,5),
(3,6),
(2,7),
(1,8),
(12,9),
(13,10);

INSERT INTO LDEPacote
(idPacote,idLDEncomenda)
VALUES
(1,1),(2,2),(3,3),
(1,11),(2,12),(3,13),
(1,14),(2,15),(3,16),
(1,21),(4,22),(1,23),(4,24)
;


INSERT INTO PeçaDoPacote
(idPacote,idPeca)
VALUES
(1,1),(1,2),(1,3),(1,4),(1,6),
(2,19),(2,20),(2,21),(2,17),
(3,14),(3,15),(3,16),(3,18),
(4,22),(4,23),(4,24),(4,25)
;

