-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Ott 24, 2018 alle 08:52
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
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `organizzazione`
--

DROP TABLE IF EXISTS `organizzazione`;
CREATE TABLE IF NOT EXISTS `organizzazione` (
  `Categoria` varchar(50) NOT NULL,
  `Opera` int(11) NOT NULL,
  PRIMARY KEY (`Categoria`,`Opera`),
  KEY `Opera` (`Opera`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `pagina`
--

DROP TABLE IF EXISTS `pagina`;
CREATE TABLE IF NOT EXISTS `pagina` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Immagine` varchar(50) NOT NULL,
  `trascrizione` text NOT NULL,
  `ult_modifica` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approvato` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `impaginazione`
--
ALTER TABLE `impaginazione`
  ADD CONSTRAINT `impaginazione_ibfk_1` FOREIGN KEY (`Opera`) REFERENCES `opera` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `impaginazione_ibfk_2` FOREIGN KEY (`Pagina`) REFERENCES `pagina` (`ID`);

--
-- Limiti per la tabella `organizzazione`
--
ALTER TABLE `organizzazione`
  ADD CONSTRAINT `organizzazione_ibfk_1` FOREIGN KEY (`Categoria`) REFERENCES `categoria` (`Nome`),
  ADD CONSTRAINT `organizzazione_ibfk_2` FOREIGN KEY (`Opera`) REFERENCES `opera` (`ID`);

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
