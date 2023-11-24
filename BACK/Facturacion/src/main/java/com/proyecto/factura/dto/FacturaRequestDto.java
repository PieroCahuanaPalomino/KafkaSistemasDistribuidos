package com.proyecto.factura.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class FacturaRequestDto {

	private String nombre;
	private String dniRuc;
	private BigDecimal totalIgv;
	private BigDecimal totalFactura;
	private Date fecha;
	
}
