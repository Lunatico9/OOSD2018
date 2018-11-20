-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Nov 20, 2018 alle 13:18
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
(28, 3, 0),
(28, 8, 1),
(28, 23, 2),
(28, 24, 3),
(28, 25, 4),
(28, 26, 5),
(28, 27, 6),
(28, 28, 7),
(28, 29, 8),
(28, 30, 9),
(28, 31, 10),
(28, 32, 11),
(28, 33, 12),
(28, 34, 13),
(28, 35, 14),
(28, 36, 15),
(28, 37, 16),
(28, 38, 17),
(28, 39, 18),
(28, 40, 19),
(28, 41, 20),
(28, 42, 21),
(28, 43, 22),
(28, 44, 23),
(28, 45, 24),
(28, 46, 25),
(28, 47, 26),
(28, 48, 27),
(28, 49, 28),
(28, 50, 29),
(28, 51, 30),
(28, 52, 31),
(28, 53, 32),
(28, 54, 33),
(28, 55, 34),
(28, 56, 35),
(28, 57, 36),
(28, 58, 37),
(28, 59, 38),
(28, 60, 39),
(28, 61, 40),
(28, 62, 41),
(28, 63, 42),
(28, 64, 43),
(28, 65, 44),
(28, 66, 45),
(28, 67, 46),
(28, 68, 47),
(28, 69, 48),
(28, 70, 49),
(28, 71, 50),
(28, 72, 51),
(28, 73, 52),
(28, 74, 53),
(28, 75, 54),
(28, 76, 55),
(28, 77, 56),
(28, 78, 57),
(28, 79, 58),
(28, 80, 59),
(28, 81, 60),
(28, 82, 61),
(28, 83, 62),
(28, 84, 63),
(28, 85, 64);

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `opera`
--

INSERT INTO `opera` (`ID`, `Titolo`, `Autore`, `Anno`, `Approvato`) VALUES
(4, 'Candido', 'Voltaire', 1789, 1),
(5, 'Contratto sociale', 'Jean-Jacques Rousseau', 1762, 1),
(6, 'L\'interpretazione dei sogni', 'Sigmund Freud', 1899, 1),
(7, 'Dei delitti e delle pene', 'Cesare Beccaria', 1764, 1),
(8, 'Eneide', 'Virgilio', -29, 1),
(9, 'Odissea', 'Omero', -600, 1),
(28, 'Opera Nova', 'Manciolino', 1531, 1);

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
('Epica', 9),
('Filosofia', 28);

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
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `pagina`
--

INSERT INTO `pagina` (`ID`, `Immagine`, `trascrizione`, `ult_modifica`, `approvato`) VALUES
(3, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-02.jpg', '<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><h2>Ciao Mondo</h2></body></html>', '2018-11-20 09:26:38', 1),
(8, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-03.jpg', '<html dir=\"ltr\"><head></head><body contenteditable=\"true\">Buonasera</body></html>', '2018-11-20 09:31:19', 1),
(21, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-02.jpg', NULL, NULL, 1),
(22, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-03.jpg', NULL, NULL, 1),
(23, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-04.jpg', NULL, NULL, 1),
(24, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-05.jpg', NULL, NULL, 1),
(25, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-06.jpg', NULL, NULL, 1),
(26, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-07.jpg', NULL, NULL, 1),
(27, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-08.jpg', NULL, NULL, 1),
(28, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-09.jpg', NULL, NULL, 1),
(29, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-10.jpg', NULL, NULL, 1),
(30, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-11.jpg', NULL, NULL, 1),
(31, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-12.jpg', NULL, NULL, 1),
(32, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-13.jpg', NULL, NULL, 1),
(33, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-14.jpg', NULL, NULL, 1),
(34, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-15.jpg', NULL, NULL, 1),
(35, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-16.jpg', NULL, NULL, 1),
(36, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-17.jpg', NULL, NULL, 1),
(37, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-18.jpg', NULL, NULL, 1),
(38, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-19.jpg', NULL, NULL, 1),
(39, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-20.jpg', NULL, NULL, 1),
(40, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-21.jpg', NULL, NULL, 1),
(41, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-22.jpg', NULL, NULL, 1),
(42, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-23.jpg', NULL, NULL, 1),
(43, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-24.jpg', NULL, NULL, 1),
(44, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-25.jpg', NULL, NULL, 1),
(45, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-26.jpg', NULL, NULL, 1),
(46, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-27.jpg', NULL, NULL, 1),
(47, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-28.jpg', NULL, NULL, 1),
(48, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-29.jpg', NULL, NULL, 1),
(49, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-30.jpg', NULL, NULL, 1),
(50, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-31.jpg', NULL, NULL, 1),
(51, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-32.jpg', NULL, NULL, 1),
(52, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-33.jpg', NULL, NULL, 1),
(53, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-34.jpg', NULL, NULL, 1),
(54, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-35.jpg', NULL, NULL, 1),
(55, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-36.jpg', NULL, NULL, 1),
(56, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-37.jpg', NULL, NULL, 1),
(57, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-38.jpg', NULL, NULL, 1),
(58, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-39.jpg', NULL, NULL, 1),
(59, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-40.jpg', NULL, NULL, 1),
(60, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-41.jpg', NULL, NULL, 1),
(61, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-42.jpg', NULL, NULL, 1),
(62, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-43.jpg', NULL, NULL, 1),
(63, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-44.jpg', NULL, NULL, 1),
(64, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-45.jpg', NULL, NULL, 1),
(65, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-46.jpg', NULL, NULL, 1),
(66, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-47.jpg', NULL, NULL, 1),
(67, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-48.jpg', NULL, NULL, 1),
(68, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-49.jpg', NULL, NULL, 1),
(69, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-50.jpg', NULL, NULL, 1),
(70, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-51.jpg', NULL, NULL, 1),
(71, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-52.jpg', NULL, NULL, 1),
(72, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-53.jpg', NULL, NULL, 1),
(73, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-54.jpg', NULL, NULL, 1),
(74, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-55.jpg', NULL, NULL, 1),
(75, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-56.jpg', NULL, NULL, 1),
(76, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-57.jpg', NULL, NULL, 1),
(77, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-58.jpg', NULL, NULL, 1),
(78, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-59.jpg', NULL, NULL, 1),
(79, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-60.jpg', NULL, NULL, 1),
(80, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-61.jpg', NULL, NULL, 1),
(81, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-62.jpg', NULL, NULL, 1),
(82, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-63.jpg', NULL, NULL, 1),
(83, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-64.jpg', NULL, NULL, 1),
(84, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-65.jpg', NULL, NULL, 1),
(85, 'file:/C:/Users/Federico/eclipse-workspace/OOSD/src/it/bibliotecadigitale/source/Manciolino_1531-66.jpg', NULL, NULL, 1);

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
('s', 8, 0),
('u', 9, 0),
('t', 10, 0),
('r', 11, 0),
('t', 12, 0);

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
(5, 28, '2018-11-20 10:17:56'),
(7, 9, '2018-11-19 17:41:27'),
(7, 28, '2018-11-20 10:29:40'),
(12, 6, '2018-11-20 10:41:50');

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
  `Mail` varchar(100) NOT NULL,
  `Titolo` varchar(50) NOT NULL,
  `Professione` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Login` (`Login`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`ID`, `Login`, `Passw`, `Privilegio`, `Nome`, `Cognome`, `Mail`, `Titolo`, `Professione`) VALUES
(4, 'ggwp', 'abcd', 1, 'gg', 'wp', 'gg@email.it', 'Diploma scuola superiore', 'Studente'),
(5, 'qualcuno', '1234', 0, 'Giovanni', 'Muciaccia', 'giova.mucia@gmail.com', 'Laurea triennale', 'Insegnante'),
(6, 'boss', 'a', 1, 'Great', 'Admin', 'admin@ymail.com', 'Amministratore', 'Amministratore'),
(7, 'ciao', 'mondo', 1, 'Sei', 'Sette', 'ciao.mondo@email.it', 'Diploma scuola media', 'Disoccupato'),
(8, 'a', 'b', 0, 'aa', 'bb', 'aabb@gmail.com', 'Diploma', 'Studente'),
(9, 'ddfsd', 'a', 0, 'sads', 'd sn', 'sadasdw', 'sss', 'dsad'),
(10, 'Nc', 'mondo', 0, 'N', 'C', 'nc', 'Dottorato', 'Professore'),
(11, 'AAVV', 'aa', 0, 'AA', 'VV', 'aavv@ymail.to', 'Boh', 'Scrittore'),
(12, 'p', 'a', 0, 'pa', 'pp', 'papppp@pp.pp', 'ppppp', 'aaaaa');

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
