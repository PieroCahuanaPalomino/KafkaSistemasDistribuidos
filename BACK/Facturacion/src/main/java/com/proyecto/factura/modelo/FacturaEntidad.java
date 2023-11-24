package com.proyecto.factura.modelo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Factura")
@Table(name = "factura")
public class FacturaEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int idFactura;

    @Column(name = "nombre_cliente")
    private String nombre;

    @Column(name = "ruc_cliente")
    private String dniRuc;

    @Column(name = "total_igv")
    private double totalIgv;

    @Column(name = "total_factura")
    private double totalFactura;

    @Column(name = "fecha_pago")
    private String fecha;
    
    @Column(name = "codigo_cliente")    
    private String codigoCliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<ItemFacturaEntidad> items;

}
