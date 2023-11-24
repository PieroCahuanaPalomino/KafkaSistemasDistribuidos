package com.proyecto.cuentas.cobrar.modelo;

import java.math.BigDecimal;
import java.util.Date;

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
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="CuentaCobrar")
@Table(name = "cuentaporcobrar")
public class CuentaCobrarEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
	private int idCuenta;
    @Column(name = "id_factura")
	private int idFactura;
    
    @Column(name = "estado_registro")
	private Boolean estadoRegistro;
    
    @Column(name = "codigo_cliente")
	private String codigoCliente;
    
    @Column(name = "nombre_cliente")
	private String nombreCliente;
    
    @Column(name = "dni_ruc")
	private String dniRuc;
    
    @Column(name = "total_igv")
	private BigDecimal totalIgv;

    @Column(name = "total_cobrar")
    private BigDecimal totalFactura;
    
    @Column(name = "fecha_cobro")
	private Date fechaCobro;
}
