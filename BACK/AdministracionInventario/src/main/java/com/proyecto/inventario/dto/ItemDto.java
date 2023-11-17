package com.proyecto.inventario.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class ItemDto {
    String codigo;
    String nombre;
    int cantidad;
    
    PedidoDto pedidoDto; 
}

