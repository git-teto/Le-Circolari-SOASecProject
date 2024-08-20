-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 09, 2024 at 03:29 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `circolari`
--

-- --------------------------------------------------------

--
-- Table structure for table `circolare`
--

CREATE TABLE `circolare` (
  `id` int(11) NOT NULL,
  `titolo` text NOT NULL,
  `descrizione` text NOT NULL,
  `data` text NOT NULL,
  `tipo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `circolare`
--

INSERT INTO `circolare` (`id`, `titolo`, `descrizione`, `data`, `tipo`) VALUES
(10, 'Lezioni sospese', 'Si avvisa che le lezioni saranno sospese per le vacanze di Pasqua da 28 Marzo al 3 Aprile 2024 ', '25-03-2024', 'studente e docente'),
(11, 'Esami di Stato', 'L\'Esame di Stato consisterà in una prova scritta e un orale', '10-04-2024', 'studente'),
(12, 'Prova di evacuazione', 'Si ricorda che la prova di evacuazione antincendio avverrà Lunedì 15 aprile 2024', '09-04-2024', 'docente'),
(13, 'Attenzione furti', 'Non lasciate incustoditi oggetti nelle aule', '11-09-2023', 'docente e studente');

-- --------------------------------------------------------

--
-- Table structure for table `utente`
--

CREATE TABLE `utente` (
  `id` int(11) NOT NULL,
  `nome` text NOT NULL,
  `cognome` text NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `tipo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utente`
--

INSERT INTO `utente` (`id`, `nome`, `cognome`, `email`, `password`, `tipo`) VALUES
(14, 'Giulio', 'Cesare', 'giulio.cesare@docente.it', '$2a$10$INoVtwTkBWKkOwxlnlN6PuDOxIq5DXPh3CPhtYPT73whcdvrQm9YW', 'docente'),
(10, 'Marco', 'Antonio', 'marco.antonio@studente.it', '$2a$10$YKAHiuaub09RTR97CJ2D1uW8RbOug9l8FVCdcCXNskdLaAprCGpJO', 'studente'),
(9, 'Mattia', 'Ruo', 'mattia.ruo@studenti.unimi.it', '$2a$10$l3gwPPUMHX.QSzo26Mw4oukd46hkRfJqqE96H2pKYbobUTf0c8KUe', 'amministratore'),
(15, 'Silvio', 'Berlusconi', 'silvio.berlusconi@presidenza.it', '$2a$10$RT.A9NyW2qvyBI41n/zY/eEfRZup3QSZx/t86yLeSrfwWIRZwbIza', 'presidenza'),
(7, 'Simone', 'Galimberti', 'simone.galimberti2@studenti.unimi.it', '$2a$10$GDl7TlT.uycQQrBVLkAItuFvxtAzVWfvXWWIVyw/exGNuMXFGe8OK', 'amministratore'),
(8, 'Stefano', 'Giardina', 'stefano.giardina@studenti.unimi.it', '$2a$10$XzL/./sBKq3J7Cv9drhQ.eOsZYIzIiab8N6VFeU7vYqnbAuisG1im', 'amministratore');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `circolare`
--
ALTER TABLE `circolare`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`email`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `circolare`
--
ALTER TABLE `circolare`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `utente`
--
ALTER TABLE `utente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
