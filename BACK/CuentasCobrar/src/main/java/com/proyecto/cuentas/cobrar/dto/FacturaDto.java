package com.proyecto.cuentas.cobrar.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class FacturaDto {
	private int idFactura;
	private String codigoCliente;
	private String nombreCliente;
	private String dniRuc;
	private BigDecimal totalIgv;
	private BigDecimal totalFactura;
	private Date fechaFactura;
	private List<ItemFacturaDto> lista;
	
}
