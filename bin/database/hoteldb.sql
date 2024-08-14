-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2024 at 06:14 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hoteldb`
--
CREATE DATABASE IF NOT EXISTS `hoteldb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `hoteldb`;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--
-- Creation: Apr 05, 2024 at 10:08 AM
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--
-- Creation: Apr 20, 2024 at 03:43 PM
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(100) NOT NULL,
  `customerID` int(11) NOT NULL,
  `roomNumber` int(100) NOT NULL,
  `roomType` varchar(100) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `phoneNumber` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `checkInDate` date NOT NULL,
  `checkOutDate` date NOT NULL,
  `total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `customerID`, `roomNumber`, `roomType`, `firstName`, `lastName`, `phoneNumber`, `email`, `checkInDate`, `checkOutDate`, `total`) VALUES
(8, 1, 9, 'Quad Room', 'gilbert', 'kukah', '0541436414', 'gkukah2@Gmail.com', '2024-04-01', '2024-04-03', 1200.00),
(9, 2, 7, 'Queen Size', 'Helina', 'Osei', '468484', 'gel@gmail.co', '2024-04-20', '2024-04-23', 2100.00),
(10, 3, 10, 'Quad Room', 'Freda', 'Sady', '021465468', 'fr@gmail.com', '2024-04-21', '2024-04-25', 3200.00),
(11, 4, 14, 'Double Room', 'Micheal', 'Anim', '0258746435', 'gkukah1@gmail.com', '2024-04-21', '2024-05-02', 2200.00),
(12, 4, 14, 'Double Room', 'Micheal', 'Anim', '0258746435', 'gkukah1@gmail.com', '2024-04-21', '2024-05-02', 2200.00),
(13, 5, 4, 'King Size', 'afd', 'afd', 'asdf', 'asdf', '2024-04-21', '2024-04-29', 4000.00),
(14, 6, 1, 'Conference Room', 'Hello', 'World', '78946312', 'gkukah1@gmail.com', '2024-04-21', '2024-04-30', 900.00),
(15, 7, 13, 'Double Room', 'ary', 'owusu', '3498752947', 'owusu@gmail.com', '2024-04-21', '2024-05-10', 7600.00),
(16, 8, 16, 'Single Room', 'The', 'Man', '094855710', 'gkukah1@gmail.com', '2024-04-20', '2024-04-24', 400.00),
(17, 10, 9, 'Quad Room', 'adsf', 'asfd', 'af', 'af', '2024-04-03', '2024-04-16', 7800.00),
(18, 11, 7, 'Queen Size', '', '', '', '', '2024-04-10', '2024-04-20', 7000.00),
(19, 12, 9, 'Quad Room', 'Emma', 'Sarpong', '0541431868', 'esarpong@gmail.com', '2024-04-22', '2024-04-28', 3600.00),
(20, 13, 4, 'King Size', 'asdfahalkfkln', 'alkgnal', 'alkgnal', 'glkangakgkl', '2024-04-25', '2024-04-30', 2500.00),
(21, 14, 9, 'Quad Room', 'Allowesd', 'Ingiahg', '7894631518', 'gaineahlkjea', '2024-04-19', '2024-04-30', 6600.00),
(22, 15, 15, 'Single Room', 'anajakljf', 'alfkjij', 'oifjaoihf', 'oifjafoe', '2024-04-19', '2024-04-25', 600.00),
(23, 16, 12, 'Triple Room', 'Buagh', 'afuabui', 'afhauihq', 'afhakjhakf', '2024-04-24', '2024-04-25', 1000.00),
(24, 16, 12, 'Triple Room', 'Buagh', 'afuabui', 'afhauihq', 'afhakjhakf', '2024-04-24', '2024-04-25', 1000.00),
(25, 17, 8, 'Quad Room', 'adfa', 'asfd', 'afd', 'af', '2024-04-23', '2024-04-25', 600.00);

-- --------------------------------------------------------

--
-- Table structure for table `customer_receipt`
--
-- Creation: Apr 19, 2024 at 11:38 AM
--

DROP TABLE IF EXISTS `customer_receipt`;
CREATE TABLE `customer_receipt` (
  `id` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `date` date NOT NULL,
  `total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_receipt`
--

INSERT INTO `customer_receipt` (`id`, `customerID`, `date`, `total`) VALUES
(5, 1, '2024-04-01', 1200.00),
(6, 2, '2024-04-20', 2100.00),
(7, 3, '2024-04-21', 3200.00),
(8, 4, '2024-04-21', 2200.00),
(9, 4, '2024-04-21', 2200.00),
(10, 5, '2024-04-21', 4000.00),
(11, 6, '2024-04-21', 900.00),
(12, 7, '2024-04-21', 7600.00),
(13, 8, '2024-04-20', 400.00),
(14, 10, '2024-04-03', 7800.00),
(15, 11, '2024-04-10', 7000.00),
(16, 12, '2024-04-22', 3600.00),
(17, 13, '2024-04-25', 2500.00),
(18, 14, '2024-04-19', 6600.00),
(19, 15, '2024-04-19', 600.00),
(20, 16, '2024-04-24', 1000.00),
(21, 16, '2024-04-24', 1000.00),
(22, 17, '2024-04-23', 600.00);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--
-- Creation: Apr 05, 2024 at 10:32 AM
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `roomNumber` int(11) NOT NULL,
  `type` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `roomNumber`, `type`, `status`, `price`) VALUES
(16, 1, 'Conference Room', 'Occupied', 100),
(17, 2, 'King Size', 'Available', 250),
(18, 3, 'King Size', 'Available', 700),
(19, 4, 'King Size', 'Occupied', 500),
(20, 5, 'Queen Size', 'Available', 200),
(21, 7, 'Queen Size', 'Occupied', 700),
(22, 8, 'Quad Room', 'Occupied', 300),
(23, 9, 'Quad Room', 'Occupied', 600),
(24, 10, 'Quad Room', 'Occupied', 800),
(25, 11, 'Triple Room', 'Available', 500),
(26, 12, 'Triple Room', 'Occupied', 1000),
(27, 13, 'Double Room', 'Occupied', 400),
(28, 15, 'Single Room', 'Occupied', 100),
(29, 14, 'Double Room', 'Occupied', 1520);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer_receipt`
--
ALTER TABLE `customer_receipt`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `customer_receipt`
--
ALTER TABLE `customer_receipt`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
