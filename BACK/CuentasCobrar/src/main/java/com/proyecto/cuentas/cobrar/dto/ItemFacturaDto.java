package com.proyecto.cuentas.cobrar.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ItemFacturaDto {
	private int idArticulo;	
	private String descripcion;
	private int cantidad;
	private BigDecimal subtotal;
}
