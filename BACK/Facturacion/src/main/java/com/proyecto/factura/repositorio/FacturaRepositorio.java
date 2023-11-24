package com.proyecto.factura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.factura.modelo.FacturaEntidad;

@Repository
public interface FacturaRepositorio extends JpaRepository<FacturaEntidad, Integer>{

}
