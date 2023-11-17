package com.proyecto.procesamiento.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class ArticuloDto {
	String codigo;
	String nombre;
	BigDecimal precio;
	int  cantidaPedida;
}
