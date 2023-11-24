package com.proyecto.factura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.factura.modelo.ItemFacturaEntidad;

@Repository
public interface ItemFacturaRepositorio extends JpaRepository<ItemFacturaEntidad, Integer>{

}
