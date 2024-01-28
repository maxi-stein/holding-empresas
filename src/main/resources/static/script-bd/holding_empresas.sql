-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-01-2024 a las 23:12:28
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `holding_empresas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `areas_asesoradas`
--

CREATE TABLE `areas_asesoradas` (
  `id_empleado_area` int(11) NOT NULL,
  `id_area_mercado_asesorada` int(11) NOT NULL,
  `areas_asesoradasid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `areas_empresa`
--

CREATE TABLE `areas_empresa` (
  `idareas_empresa` int(11) NOT NULL,
  `id_empresa_mercados` int(11) NOT NULL,
  `id_area_mercado_empresa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `areas_mercado`
--

CREATE TABLE `areas_mercado` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `eliminado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asesores_empresa`
--

CREATE TABLE `asesores_empresa` (
  `idasesores_empresa` int(11) NOT NULL,
  `id_empresa_relacion` int(11) NOT NULL,
  `id_empleado_empresa` int(11) NOT NULL,
  `fecha_inicio` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudades`
--

CREATE TABLE `ciudades` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `eliminado` int(11) NOT NULL,
  `id_pais` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad_empresa`
--

CREATE TABLE `ciudad_empresa` (
  `idciudad_empresa` int(11) NOT NULL,
  `id_empresa_ubicada` int(11) NOT NULL,
  `id_ciudad_empresa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id` int(11) NOT NULL,
  `tipo` varchar(5) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `titulacion` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) NOT NULL,
  `id_empresa` int(11) DEFAULT NULL,
  `eliminado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresas`
--

CREATE TABLE `empresas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `sede_id` int(11) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `facturacion` decimal(10,0) NOT NULL,
  `eliminado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paises`
--

CREATE TABLE `paises` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `PBI` decimal(10,0) NOT NULL,
  `capital` int(11) NOT NULL,
  `habitantes` bigint(20) NOT NULL,
  `eliminado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedores_captados`
--

CREATE TABLE `vendedores_captados` (
  `id_empleado` int(11) NOT NULL,
  `id_empleado_captado` int(11) NOT NULL,
  `fecha_captado` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `areas_asesoradas`
--
ALTER TABLE `areas_asesoradas`
  ADD PRIMARY KEY (`areas_asesoradasid`),
  ADD KEY `id_empleado_area` (`id_empleado_area`),
  ADD KEY `id_area_mercado_asesorada` (`id_area_mercado_asesorada`);

--
-- Indices de la tabla `areas_empresa`
--
ALTER TABLE `areas_empresa`
  ADD PRIMARY KEY (`idareas_empresa`),
  ADD KEY `id_empresa_mercados` (`id_empresa_mercados`),
  ADD KEY `id_area_mercado_empresa` (`id_area_mercado_empresa`);

--
-- Indices de la tabla `areas_mercado`
--
ALTER TABLE `areas_mercado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `asesores_empresa`
--
ALTER TABLE `asesores_empresa`
  ADD PRIMARY KEY (`idasesores_empresa`),
  ADD KEY `id_empresa_relacion` (`id_empresa_relacion`),
  ADD KEY `id_empleado_empresa` (`id_empleado_empresa`);

--
-- Indices de la tabla `ciudades`
--
ALTER TABLE `ciudades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pais` (`id_pais`);

--
-- Indices de la tabla `ciudad_empresa`
--
ALTER TABLE `ciudad_empresa`
  ADD PRIMARY KEY (`idciudad_empresa`),
  ADD KEY `id_empresa_ubicada` (`id_empresa_ubicada`),
  ADD KEY `id_ciudad_empresa` (`id_ciudad_empresa`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_empresa` (`id_empresa`);

--
-- Indices de la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sede` (`sede_id`);

--
-- Indices de la tabla `paises`
--
ALTER TABLE `paises`
  ADD PRIMARY KEY (`id`),
  ADD KEY `capital` (`capital`);

--
-- Indices de la tabla `vendedores_captados`
--
ALTER TABLE `vendedores_captados`
  ADD KEY `id_empleado` (`id_empleado`),
  ADD KEY `id_empleado_captado` (`id_empleado_captado`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `areas_asesoradas`
--
ALTER TABLE `areas_asesoradas`
  MODIFY `areas_asesoradasid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `areas_empresa`
--
ALTER TABLE `areas_empresa`
  MODIFY `idareas_empresa` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `areas_mercado`
--
ALTER TABLE `areas_mercado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `asesores_empresa`
--
ALTER TABLE `asesores_empresa`
  MODIFY `idasesores_empresa` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ciudades`
--
ALTER TABLE `ciudades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ciudad_empresa`
--
ALTER TABLE `ciudad_empresa`
  MODIFY `idciudad_empresa` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empresas`
--
ALTER TABLE `empresas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `paises`
--
ALTER TABLE `paises`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `areas_asesoradas`
--
ALTER TABLE `areas_asesoradas`
  ADD CONSTRAINT `id_area_mercado_asesorada` FOREIGN KEY (`id_area_mercado_asesorada`) REFERENCES `areas_mercado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `id_empleado_area` FOREIGN KEY (`id_empleado_area`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `areas_empresa`
--
ALTER TABLE `areas_empresa`
  ADD CONSTRAINT `id_area_mercado_empresa` FOREIGN KEY (`id_area_mercado_empresa`) REFERENCES `areas_mercado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `id_empresa_mercados` FOREIGN KEY (`id_empresa_mercados`) REFERENCES `empresas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `asesores_empresa`
--
ALTER TABLE `asesores_empresa`
  ADD CONSTRAINT `id_empleado_empresa` FOREIGN KEY (`id_empleado_empresa`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `id_empresa_relacion` FOREIGN KEY (`id_empresa_relacion`) REFERENCES `empresas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ciudades`
--
ALTER TABLE `ciudades`
  ADD CONSTRAINT `id_pais` FOREIGN KEY (`id_pais`) REFERENCES `paises` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ciudad_empresa`
--
ALTER TABLE `ciudad_empresa`
  ADD CONSTRAINT `id_ciudad_empresa` FOREIGN KEY (`id_ciudad_empresa`) REFERENCES `ciudades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `id_empresa_ubicada` FOREIGN KEY (`id_empresa_ubicada`) REFERENCES `empresas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD CONSTRAINT `id_empresa` FOREIGN KEY (`id_empresa`) REFERENCES `empresas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD CONSTRAINT `sede` FOREIGN KEY (`sede_id`) REFERENCES `ciudades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `paises`
--
ALTER TABLE `paises`
  ADD CONSTRAINT `capital` FOREIGN KEY (`capital`) REFERENCES `ciudades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vendedores_captados`
--
ALTER TABLE `vendedores_captados`
  ADD CONSTRAINT `id_empleado` FOREIGN KEY (`id_empleado`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `id_empleado_captado` FOREIGN KEY (`id_empleado_captado`) REFERENCES `empleados` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
