-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for mtms
CREATE DATABASE IF NOT EXISTS `mtms` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mtms`;

-- Dumping structure for table mtms.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `balance` int(5) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `mobile_number` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table mtms.customer: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping structure for table mtms.pink_line
CREATE TABLE IF NOT EXISTS `pink_line` (
  `station` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table mtms.pink_line: ~38 rows (approximately)
/*!40000 ALTER TABLE `pink_line` DISABLE KEYS */;
INSERT INTO `pink_line` (`station`) VALUES
	('Shiv Vihar'),
	('Johri Enclave'),
	('Gokulpuri'),
	('Maujpur-Babarpur'),
	('Jaffrabad'),
	('Welcome'),
	('East Azad Nagar'),
	('Krishna Nagar'),
	('Karkarduma Court'),
	('Karkarduma'),
	('Anand Vihar'),
	('IP Extension'),
	('Mandawali-West Vinod Nagar'),
	('East Vinod Nagar-Mayur Vihar-II'),
	('Trilokpuri-Sanjay Lake'),
	('Mayur Vihar Pocket I'),
	('Mayur Vihar - I'),
	('Sarai Kale Khan - Nizamuddin'),
	('Ashram'),
	('Vinobapuri'),
	('Lajpat Nagar'),
	('South Extension'),
	('INA'),
	('Sarojini Nagar'),
	('Bhikaji Kama Place'),
	('Sir Vishweshwaraiah Moti Bagh'),
	('Durgabai Deshmukh South Campus'),
	('Delhi Cantonment'),
	('Naraina Vihar'),
	('Mayapuri'),
	('Rajouri Garden'),
	('ESI Hospital'),
	('Punjabi Bagh west'),
	('Shakurpur'),
	('Netaji Subash Place'),
	('Shalimar Bagh'),
	('Azadpur'),
	('Majlis Park');
/*!40000 ALTER TABLE `pink_line` ENABLE KEYS */;

-- Dumping structure for table mtms.red_line
CREATE TABLE IF NOT EXISTS `red_line` (
  `station` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table mtms.red_line: ~29 rows (approximately)
/*!40000 ALTER TABLE `red_line` DISABLE KEYS */;
INSERT INTO `red_line` (`station`) VALUES
	('Rithala'),
	('Rohini West'),
	('Rohini East'),
	('Pitampura'),
	('Kohat Enclave'),
	('Netaji Subhash Place'),
	('Kesav Puram'),
	('Kanhaiya Nagar'),
	('Inderlok'),
	('Shastri Nagar'),
	('Pratap Nagar'),
	('Pul Bangash'),
	('Tis Hazari'),
	('Kashmere Gate'),
	('Shastri Park'),
	('Seelampur'),
	('Welcome'),
	('Shahdara'),
	('Mansarovar Park'),
	('Jhilmil'),
	('Dilshad Garden'),
	('Shaheed Nagar'),
	('Raj Bagh'),
	('Major Mohit Sharma'),
	('Shyam Park'),
	('Mohan Nagar'),
	('Arthala'),
	('Hindon'),
	('Shaheed Sthal');
/*!40000 ALTER TABLE `red_line` ENABLE KEYS */;

-- Dumping structure for table mtms.staff
CREATE TABLE IF NOT EXISTS `staff` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `mobile_number` bigint(10) DEFAULT NULL,
  `salary` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table mtms.staff: ~0 rows (approximately)
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;

-- Dumping structure for table mtms.yellow_line
CREATE TABLE IF NOT EXISTS `yellow_line` (
  `station` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table mtms.yellow_line: ~37 rows (approximately)
/*!40000 ALTER TABLE `yellow_line` DISABLE KEYS */;
INSERT INTO `yellow_line` (`station`) VALUES
	('HUDA City Centre'),
	('IFFCO Chowk'),
	('MG Road'),
	('Sikandarpur'),
	('Guru Dronacharya'),
	('Arjan Garh'),
	('Ghitorni'),
	('Sultanpur'),
	('Chhatarpur'),
	('Qutub Minar'),
	('Saket'),
	('Malviya Nagar'),
	('Hauz Khas'),
	('Green Park'),
	('AIIMS'),
	('INA'),
	('Jor Bagh'),
	('Lok Kalyan Marg'),
	('Udyog Bhawan'),
	('Central Secretariat'),
	('Patel Chowk'),
	('Rajiv Chowk'),
	('New Delhi'),
	('Chawri Bazar'),
	('Chandni Chowk'),
	('Kashmere Gate'),
	('Civil Lines'),
	('Vidhan Sabha'),
	('Vishwa Vidyalaya'),
	('GTB Nagar'),
	('Model Town'),
	('Azadpur'),
	('Adarsh Nagar'),
	('Jahangirpuri'),
	('Haiderpur Badli Mor'),
	('Rohini Sector 18, 19'),
	('Samaypur Badli');
/*!40000 ALTER TABLE `yellow_line` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
