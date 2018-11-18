-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Nov 18, 2018 alle 21:50
-- Versione del server: 5.7.23
-- Versione PHP: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `oosd`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `categoria`
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `Nome` varchar(50) NOT NULL,
  PRIMARY KEY (`Nome`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `categoria`
--

INSERT INTO `categoria` (`Nome`) VALUES
('Epica'),
('Filosofia'),
('Fisica'),
('Psicologia'),
('Sociologia');

-- --------------------------------------------------------

--
-- Struttura della tabella `impaginazione`
--

DROP TABLE IF EXISTS `impaginazione`;
CREATE TABLE IF NOT EXISTS `impaginazione` (
  `Opera` int(11) NOT NULL,
  `Pagina` int(11) NOT NULL,
  `Numero` int(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`Pagina`,`Opera`),
  KEY `Opera` (`Opera`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `impaginazione`
--

INSERT INTO `impaginazione` (`Opera`, `Pagina`, `Numero`) VALUES
(5, 3, 1),
(5, 8, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `opera`
--

DROP TABLE IF EXISTS `opera`;
CREATE TABLE IF NOT EXISTS `opera` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(50) NOT NULL,
  `Autore` varchar(50) NOT NULL,
  `Anno` int(50) NOT NULL,
  `Approvato` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `Titolo` (`Titolo`,`Autore`,`Anno`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `opera`
--

INSERT INTO `opera` (`ID`, `Titolo`, `Autore`, `Anno`, `Approvato`) VALUES
(4, 'Candido', 'Voltaire', 1789, 1),
(5, 'Contratto sociale', 'Jean-Jacques Rousseau', 1762, 1),
(6, 'L\'interpretazione dei sogni', 'Sigmund Freud', 1899, 1),
(7, 'Dei delitti e delle pene', 'Cesare Beccaria', 1764, 1),
(8, 'Eneide', 'Virgilio', -29, 1),
(9, 'Odissea', 'Omero', -600, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `organizzazione`
--

DROP TABLE IF EXISTS `organizzazione`;
CREATE TABLE IF NOT EXISTS `organizzazione` (
  `Categoria` varchar(50) NOT NULL,
  `Opera` int(11) NOT NULL,
  PRIMARY KEY (`Categoria`,`Opera`),
  KEY `organizzazione_ibfk_2` (`Opera`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `organizzazione`
--

INSERT INTO `organizzazione` (`Categoria`, `Opera`) VALUES
('Filosofia', 4),
('Sociologia', 5),
('Psicologia', 6),
('Sociologia', 7),
('Epica', 8),
('Epica', 9);

-- --------------------------------------------------------

--
-- Struttura della tabella `pagina`
--

DROP TABLE IF EXISTS `pagina`;
CREATE TABLE IF NOT EXISTS `pagina` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Immagine` varchar(150) NOT NULL,
  `trascrizione` text,
  `ult_modifica` timestamp NULL DEFAULT NULL,
  `approvato` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `pagina`
--

INSERT INTO `pagina` (`ID`, `Immagine`, `trascrizione`, `ult_modifica`, `approvato`) VALUES
(3, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-02.jpg', 'Trascrizione', '2018-11-18 17:01:38', 1),
(8, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-03.jpg', 'Trascrizione 2', '2018-11-08 23:00:00', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `ruolo`
--

DROP TABLE IF EXISTS `ruolo`;
CREATE TABLE IF NOT EXISTS `ruolo` (
  `Nome` char(1) NOT NULL,
  `Utente` int(11) NOT NULL,
  `Livello` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Utente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `ruolo`
--

INSERT INTO `ruolo` (`Nome`, `Utente`, `Livello`) VALUES
('u', 4, 0),
('t', 5, 2),
('a', 6, 0),
('t', 7, 0),
('m', 8, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `trascrittore`
--

DROP TABLE IF EXISTS `trascrittore`;
CREATE TABLE IF NOT EXISTS `trascrittore` (
  `Utente` int(11) NOT NULL,
  `Opera` int(11) NOT NULL,
  `Data` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Utente`,`Opera`),
  KEY `Opera` (`Opera`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `trascrittore`
--

INSERT INTO `trascrittore` (`Utente`, `Opera`, `Data`) VALUES
(4, 5, '2018-10-24 12:46:24');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

DROP TABLE IF EXISTS `utente`;
CREATE TABLE IF NOT EXISTS `utente` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(50) NOT NULL,
  `Passw` varchar(50) NOT NULL,
  `Privilegio` tinyint(1) NOT NULL DEFAULT '0',
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Login` (`Login`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`ID`, `Login`, `Passw`, `Privilegio`, `Nome`, `Cognome`) VALUES
(4, 'ggwp', 'abcd', 1, 'gg', 'wp'),
(5, 'qualcuno', '1234', 0, 'Giovanni', 'Muciaccia'),
(6, 'boss', 'a', 1, 'Great', 'Admin'),
(7, 'ciao', 'mondo', 1, 'Sei', 'Sette'),
(8, 'a', 'b', 0, 'aa', 'bb');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `impaginazione`
--
ALTER TABLE `impaginazione`
  ADD CONSTRAINT `impaginazione_ibfk_1` FOREIGN KEY (`Opera`) REFERENCES `opera` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `impaginazione_ibfk_2` FOREIGN KEY (`Pagina`) REFERENCES `pagina` (`ID`) ON DELETE CASCADE;

--
-- Limiti per la tabella `organizzazione`
--
ALTER TABLE `organizzazione`
  ADD CONSTRAINT `organizzazione_ibfk_1` FOREIGN KEY (`Categoria`) REFERENCES `categoria` (`Nome`),
  ADD CONSTRAINT `organizzazione_ibfk_2` FOREIGN KEY (`Opera`) REFERENCES `opera` (`ID`) ON DELETE CASCADE;

--
-- Limiti per la tabella `ruolo`
--
ALTER TABLE `ruolo`
  ADD CONSTRAINT `ruolo_ibfk_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`ID`) ON DELETE CASCADE;

--
-- Limiti per la tabella `trascrittore`
--
ALTER TABLE `trascrittore`
  ADD CONSTRAINT `trascrittore_ibfk_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `trascrittore_ibfk_2` FOREIGN KEY (`Opera`) REFERENCES `opera` (`ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
