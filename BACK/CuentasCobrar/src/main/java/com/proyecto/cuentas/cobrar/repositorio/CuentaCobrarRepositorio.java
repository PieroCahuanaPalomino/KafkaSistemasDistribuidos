package com.proyecto.cuentas.cobrar.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.cuentas.cobrar.modelo.CuentaCobrarEntidad;

public interface CuentaCobrarRepositorio extends JpaRepository<CuentaCobrarEntidad, Integer>{

}
