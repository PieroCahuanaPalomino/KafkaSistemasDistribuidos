package com.proyecto.inventario.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class ArticuloDto {
	String codigoArticulo;
	String nombreArticulo;
	BigDecimal precioUnitario;
	int  cantidadPedido;
}


