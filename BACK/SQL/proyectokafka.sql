-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2023 a las 06:14:45
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectokafka`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE `articulo` (
  `ID_articulo` int(11) NOT NULL,
  `codigoArticulo` varchar(10) NOT NULL,
  `nombreArticulo` varchar(120) NOT NULL,
  `precioUnitario` decimal(10,3) NOT NULL,
  `cantidadDisponible` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `articulo`
--

INSERT INTO `articulo` (`ID_articulo`, `codigoArticulo`, `nombreArticulo`, `precioUnitario`, `cantidadDisponible`) VALUES
(1, 'A325OP', 'Soporte de Laptop', 82.500, 40),
(2, 'A378MG', 'Mouse Ergonómico', 198.950, 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentaporcobrar`
--

CREATE TABLE `cuentaporcobrar` (
  `ID_CuentaPorCobrar` int(11) NOT NULL,
  `ID_factura` int(11) NOT NULL,
  `estadoRegistro` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `ID_factura` int(11) NOT NULL,
  `nombreCliente` varchar(120) NOT NULL,
  `rucCliente` varchar(11) NOT NULL,
  `totalIGV` decimal(10,3) NOT NULL,
  `totalFactura` decimal(10,3) NOT NULL,
  `fechaPago` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `itemfactura`
--

CREATE TABLE `itemfactura` (
  `codigoItem` int(11) NOT NULL,
  `ID_factura` int(11) NOT NULL,
  `ID_articulo` int(11) NOT NULL,
  `descripcionItem` varchar(120) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `subtotal` decimal(10,3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD PRIMARY KEY (`ID_articulo`);

--
-- Indices de la tabla `cuentaporcobrar`
--
ALTER TABLE `cuentaporcobrar`
  ADD PRIMARY KEY (`ID_CuentaPorCobrar`),
  ADD KEY `cuentaporcobrar_fk0` (`ID_factura`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`ID_factura`);

--
-- Indices de la tabla `itemfactura`
--
ALTER TABLE `itemfactura`
  ADD PRIMARY KEY (`codigoItem`),
  ADD KEY `itemfactura_fk0` (`ID_articulo`),
  ADD KEY `itemfactura_fk1` (`ID_factura`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulo`
--
ALTER TABLE `articulo`
  MODIFY `ID_articulo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cuentaporcobrar`
--
ALTER TABLE `cuentaporcobrar`
  MODIFY `ID_CuentaPorCobrar` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `ID_factura` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `itemfactura`
--
ALTER TABLE `itemfactura`
  MODIFY `codigoItem` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cuentaporcobrar`
--
ALTER TABLE `cuentaporcobrar`
  ADD CONSTRAINT `cuentaporcobrar_fk0` FOREIGN KEY (`ID_factura`) REFERENCES `factura` (`ID_factura`);

--
-- Filtros para la tabla `itemfactura`
--
ALTER TABLE `itemfactura`
  ADD CONSTRAINT `itemfactura_fk0` FOREIGN KEY (`ID_articulo`) REFERENCES `articulo` (`ID_articulo`),
  ADD CONSTRAINT `itemfactura_fk1` FOREIGN KEY (`ID_factura`) REFERENCES `factura` (`ID_factura`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
