package com.proyecto.factura.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="ItemFactura")
@Table(name = "itemfactura")
public class ItemFacturaEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_item")
    private int codigoItem;

    @ManyToOne
    @JoinColumn(name = "id_factura")
    private FacturaEntidad factura;

    @Column(name = "id_articulo")
    private int idArticulo;

    @Column(name = "descripcion_item")
    private String descripcion;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "subtotal")
    private BigDecimal subtotal;
}
