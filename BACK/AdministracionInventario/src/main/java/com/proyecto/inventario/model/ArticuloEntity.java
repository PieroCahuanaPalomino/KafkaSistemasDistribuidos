package com.proyecto.inventario.model;



import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Arituculo")
@Table(name = "ariticulo")
public class ArticuloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_articulo")
    private Long id;

    // Otras propiedades de Item
    @Column(name = "codigoArticulo")
    private String codigo;
    
    @Column(name = "nombreArticulo")
    private String nombre;
    
    @Column(name = "precioUnitario")
    private BigDecimal precio;
    
    @Column(name = "cantidadDisponible")
    private int cantidadDisponible;
    

}


