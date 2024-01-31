-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: holding_empresas
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `areas_asesoradas`
--

DROP DATABASE IF EXISTS holding_empresas;

CREATE DATABASE holding_empresas;

USE holding_empresas;

DROP TABLE IF EXISTS `areas_asesoradas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areas_asesoradas` (
  `id_empleado_area` int NOT NULL,
  `id_area_mercado_asesorada` int NOT NULL,
  UNIQUE KEY `idx_areas_asesoradas_id_area_mercado_asesorada_id_empleado_area` (`id_area_mercado_asesorada`,`id_empleado_area`),
  KEY `id_empleado_area` (`id_empleado_area`),
  KEY `id_area_mercado_asesorada` (`id_area_mercado_asesorada`),
  CONSTRAINT `id_area_mercado_asesorada` FOREIGN KEY (`id_area_mercado_asesorada`) REFERENCES `areas_mercado` (`id`),
  CONSTRAINT `id_empleado_area` FOREIGN KEY (`id_empleado_area`) REFERENCES `empleados` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas_asesoradas`
--

LOCK TABLES `areas_asesoradas` WRITE;
/*!40000 ALTER TABLE `areas_asesoradas` DISABLE KEYS */;
INSERT INTO `areas_asesoradas` VALUES (11,1),(12,3),(13,4),(14,2);
/*!40000 ALTER TABLE `areas_asesoradas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `areas_empresa`
--

DROP TABLE IF EXISTS `areas_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areas_empresa` (
  `id_empresa_mercados` int NOT NULL,
  `id_area_mercado_empresa` int NOT NULL,
  UNIQUE KEY `idx_areas_empresa_id_empresa_mercados_id_area_mercado_empresa` (`id_empresa_mercados`,`id_area_mercado_empresa`),
  KEY `id_empresa_mercados` (`id_empresa_mercados`),
  KEY `id_area_mercado_empresa` (`id_area_mercado_empresa`),
  CONSTRAINT `id_area_mercado_empresa` FOREIGN KEY (`id_area_mercado_empresa`) REFERENCES `areas_mercado` (`id`),
  CONSTRAINT `id_empresa_mercados` FOREIGN KEY (`id_empresa_mercados`) REFERENCES `empresas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas_empresa`
--

LOCK TABLES `areas_empresa` WRITE;
/*!40000 ALTER TABLE `areas_empresa` DISABLE KEYS */;
INSERT INTO `areas_empresa` VALUES (1,1),(2,3),(3,4),(4,2);
/*!40000 ALTER TABLE `areas_empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `areas_mercado`
--

DROP TABLE IF EXISTS `areas_mercado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areas_mercado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `descripcion` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `eliminado` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `areas_mercado`
--

LOCK TABLES `areas_mercado` WRITE;
/*!40000 ALTER TABLE `areas_mercado` DISABLE KEYS */;
INSERT INTO `areas_mercado` VALUES (1,'Desarrollo de Software','Creación innovadora de programas y aplicaciones para satisfacer necesidades tecnológicas y mejorar la eficiencia.',0),(2,'Energías renovables','Generación sostenible de energía a través de fuentes limpias y naturales',0),(3,'Investigación y desarrollo médico','Avances científicos para descubrir y desarrollar tratamientos médicos innovadores y mejorar la atención de la salud.',0),(4,'Bancos y servicios financiero','Facilitación de transacciones financieras, inversiones y servicios bancarios para clientes y empresas.',0),(5,'Construcción y contratistas','Ejecución eficiente de proyectos de construcción y servicios de contratistas para desarrollar infraestructuras y edificaciones.',0);
/*!40000 ALTER TABLE `areas_mercado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asesores_empresa`
--

DROP TABLE IF EXISTS `asesores_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asesores_empresa` (
  `id_empresa_relacion` int NOT NULL,
  `id_empleado_empresa` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  KEY `id_empresa_relacion` (`id_empresa_relacion`),
  KEY `id_empleado_empresa` (`id_empleado_empresa`),
  CONSTRAINT `id_empleado_empresa` FOREIGN KEY (`id_empleado_empresa`) REFERENCES `empleados` (`id`),
  CONSTRAINT `id_empresa_relacion` FOREIGN KEY (`id_empresa_relacion`) REFERENCES `empresas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asesores_empresa`
--

LOCK TABLES `asesores_empresa` WRITE;
/*!40000 ALTER TABLE `asesores_empresa` DISABLE KEYS */;
INSERT INTO `asesores_empresa` VALUES (1,11,'2022-08-01'),(2,12,'2019-03-01'),(3,13,'2024-12-12'),(4,14,'2024-08-09');
/*!40000 ALTER TABLE `asesores_empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudad_empresa`
--

DROP TABLE IF EXISTS `ciudad_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ciudad_empresa` (
  `id_empresa_ubicada` int NOT NULL,
  `id_ciudad_empresa` int NOT NULL,
  UNIQUE KEY `idx_ciudad_empresa_id_empresa_ubicada_id_ciudad_empresa` (`id_empresa_ubicada`,`id_ciudad_empresa`),
  KEY `id_empresa_ubicada` (`id_empresa_ubicada`),
  KEY `id_ciudad_empresa` (`id_ciudad_empresa`),
  CONSTRAINT `id_ciudad_empresa` FOREIGN KEY (`id_ciudad_empresa`) REFERENCES `ciudades` (`id`),
  CONSTRAINT `id_empresa_ubicada` FOREIGN KEY (`id_empresa_ubicada`) REFERENCES `empresas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudad_empresa`
--

LOCK TABLES `ciudad_empresa` WRITE;
/*!40000 ALTER TABLE `ciudad_empresa` DISABLE KEYS */;
INSERT INTO `ciudad_empresa` VALUES (1,3),(2,1),(3,4),(4,6);
/*!40000 ALTER TABLE `ciudad_empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudades`
--

DROP TABLE IF EXISTS `ciudades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ciudades` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `eliminado` int NOT NULL,
  `id_pais` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pais` (`id_pais`),
  CONSTRAINT `id_pais` FOREIGN KEY (`id_pais`) REFERENCES `paises` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudades`
--

LOCK TABLES `ciudades` WRITE;
/*!40000 ALTER TABLE `ciudades` DISABLE KEYS */;
INSERT INTO `ciudades` VALUES (1,'Washington D.C.',0,1),(2,'Pekín',0,1),(3,'Los Ángeles',0,2),(4,'Buenos Aires',0,2),(5,'Shanghái',0,3),(6,'Córdoba',0,3),(7,'Rosario',0,3),(8,'Mendoza',0,3);
/*!40000 ALTER TABLE `ciudades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nombre` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `apellido` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `titulacion` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `direccion` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_empresa` int DEFAULT NULL,
  `eliminado` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_empresa` (`id_empresa`),
  CONSTRAINT `id_empresa` FOREIGN KEY (`id_empresa`) REFERENCES `empresas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES (1,'adm','Maximiliano','Stein',NULL,NULL,NULL,0),(2,'adm','Matias','Di Lella',NULL,NULL,NULL,0),(3,'vend','Marty','McFly',NULL,'5th Avenue 87',1,0),(4,'vend','Leo','Messi',NULL,'2nd Avenue 1253',1,0),(5,'vend','Xin','Pilin',NULL,'Xao Ti 567',2,0),(6,'vend','Eloisa','Fengwen',NULL,'Fan Fing 9485',2,0),(7,'vend','Nicolas','Guitierrez',NULL,'Av. Libertador 123',3,0),(8,'vend','Jose','Bach',NULL,'Suipacha 1123',3,0),(9,'vend','Kevin','Marengo',NULL,'Av Amancio Alcorta 34',4,0),(10,'vend','Silvia','Benitez',NULL,'Ministro Carranza 2340',4,0),(11,'ases','Joe','Trump','Software Senior Engineer','7th Avenue 8137',1,0),(12,'ases','Mingo','Chen','Magister Medicine - Heart Specialist','Xang Ten 1042',2,0),(13,'ases','Pedro','Giordano','MBA - Master in Buisiness Associate','Lola Mora 456',3,0),(14,'ases','Sandra','Guevara','Mechanical Engineer','5th Avenue 87',4,0);
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresas`
--

DROP TABLE IF EXISTS `empresas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sede_id` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  `facturacion` decimal(10,0) NOT NULL,
  `eliminado` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sede` (`sede_id`),
  CONSTRAINT `sede` FOREIGN KEY (`sede_id`) REFERENCES `ciudades` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresas`
--

LOCK TABLES `empresas` WRITE;
/*!40000 ALTER TABLE `empresas` DISABLE KEYS */;
INSERT INTO `empresas` VALUES (1,'SoftTech Innovators',1,'2024-01-01',1580000,0),(2,'Yuan Medicine',2,'2024-01-02',3800000,0),(3,'Global Argenbank',4,'2024-01-03',1200000,0),(4,'Cuyo Energy',8,'2024-01-04',500000,0);
/*!40000 ALTER TABLE `empresas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paises`
--

DROP TABLE IF EXISTS `paises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paises` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `PBI` decimal(10,0) NOT NULL,
  `capital_id` int DEFAULT NULL,
  `habitantes` bigint NOT NULL,
  `eliminado` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `capital` (`capital_id`),
  CONSTRAINT `capital` FOREIGN KEY (`capital_id`) REFERENCES `ciudades` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paises`
--

LOCK TABLES `paises` WRITE;
/*!40000 ALTER TABLE `paises` DISABLE KEYS */;
INSERT INTO `paises` VALUES (1,'Estados Unidos',21433225,1,331002651,0),(2,'China',16768184,2,1444216107,0),(3,'Argentina',637486,4,45195777,0);
/*!40000 ALTER TABLE `paises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendedores_captados`
--

DROP TABLE IF EXISTS `vendedores_captados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendedores_captados` (
  `id_empleado` int NOT NULL,
  `id_empleado_captado` int NOT NULL,
  `fecha_captado` date NOT NULL,
  KEY `id_empleado` (`id_empleado`),
  KEY `id_empleado_captado` (`id_empleado_captado`),
  CONSTRAINT `id_empleado` FOREIGN KEY (`id_empleado`) REFERENCES `empleados` (`id`),
  CONSTRAINT `id_empleado_captado` FOREIGN KEY (`id_empleado_captado`) REFERENCES `empleados` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendedores_captados`
--

LOCK TABLES `vendedores_captados` WRITE;
/*!40000 ALTER TABLE `vendedores_captados` DISABLE KEYS */;
INSERT INTO `vendedores_captados` VALUES (4,3,'2023-08-12'),(6,5,'2021-02-27'),(7,8,'2024-01-01'),(9,10,'2020-05-24');
/*!40000 ALTER TABLE `vendedores_captados` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-30 21:28:18
