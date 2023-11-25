package com.proyecto.cuentas.cobrar.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.cuentas.cobrar.modelo.CuentaCobrarEntidad;

@Repository
public interface CuentaCobrarRepositorio extends JpaRepository<CuentaCobrarEntidad, Integer>{

}
