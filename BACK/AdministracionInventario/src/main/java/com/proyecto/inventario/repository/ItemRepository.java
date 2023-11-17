package com.proyecto.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.inventario.model.ArticuloEntity;

@Repository
public interface ItemRepository extends JpaRepository<ArticuloEntity,Long>{

}
