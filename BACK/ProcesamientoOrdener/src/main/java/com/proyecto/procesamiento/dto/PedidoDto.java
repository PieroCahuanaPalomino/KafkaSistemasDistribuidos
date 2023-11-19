package com.proyecto.procesamiento.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class PedidoDto {
	List<ArticuloDto> listaArticulos;
	CompradorDto comprador;
}
