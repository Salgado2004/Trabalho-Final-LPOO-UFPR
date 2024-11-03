CREATE DATABASE `bancotads`
USE `bancoTads`;

CREATE TABLE `endereco` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` int NOT NULL,
  `logradouro` varchar(50) NOT NULL,
  `bairro` varchar(50) NOT NULL,
  `cidade` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `cliente` (
  `cpf` varchar(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `sobrenome` varchar(50) NOT NULL,
  `rg` varchar(14) NOT NULL,
  `idEndereco` int NOT NULL,
  `numeroConta` int DEFAULT NULL,
  PRIMARY KEY (`cpf`)
);

CREATE TABLE `conta` (
  `numero` int NOT NULL AUTO_INCREMENT,
  `cpfCliente` varchar(11) NOT NULL,
  `saldo` float NOT NULL,
  PRIMARY KEY (`numero`),
  KEY `cpfCliente` (`cpfCliente`),
  CONSTRAINT `fk_cliente` FOREIGN KEY (`cpfCliente`) REFERENCES `cliente` (`cpf`)
);

CREATE TABLE `contacorrente` (
  `numeroConta` int NOT NULL,
  `depositoInicial` float DEFAULT NULL,
  `limite` float NOT NULL,
  PRIMARY KEY (`numeroConta`),
  CONSTRAINT `fk_conta` FOREIGN KEY (`numeroConta`) REFERENCES `conta` (`numero`)
);

CREATE TABLE `containvestimento` (
  `numeroConta` int NOT NULL,
  `depositoInicial` float DEFAULT NULL,
  `depositoMinimo` float NOT NULL,
  `montanteMinimo` float NOT NULL,
  PRIMARY KEY (`numeroConta`),
  CONSTRAINT `fk_conta` FOREIGN KEY (`numeroConta`) REFERENCES `conta` (`numero`)
);

ALTER TABLE cliente ADD CONSTRAINT `fk_conta` FOREIGN KEY (`numeroConta`) REFERENCES `conta` (`numero`);
ALTER TABLE cliente ADD CONSTRAINT `fk_endereco` FOREIGN KEY (`idEndereco`) REFERENCES `endereco` (`id`);