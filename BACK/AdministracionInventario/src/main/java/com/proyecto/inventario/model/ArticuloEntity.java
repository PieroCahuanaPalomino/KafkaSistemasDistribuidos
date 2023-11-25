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
@Entity(name="Articulo")
@Table(name = "articulo")
public class ArticuloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private int id;

    // Otras propiedades de Item
    @Column(name = "codigo_articulo")
    private String codigoArticulo;
    
    @Column(name = "nombre_articulo")
    private String nombreArticulo;
    
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    
    @Column(name = "cantidad_disponible")
    private int cantidadDisponible;
    
    
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
}


