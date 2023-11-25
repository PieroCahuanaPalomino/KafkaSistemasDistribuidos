package com.proyecto.inventario.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
public class PedidoDto {
	List<ArticuloDto> listaArticulos;
	CompradorDto comprador;
	public List<ArticuloDto> getListaArticulos() {
		return listaArticulos;
	}
	public void setListaArticulos(List<ArticuloDto> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}
	public CompradorDto getComprador() {
		return comprador;
	}
	public void setComprador(CompradorDto comprador) {
		this.comprador = comprador;
	}
	
	
}

