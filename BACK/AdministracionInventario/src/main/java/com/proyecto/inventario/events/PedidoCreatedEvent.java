package com.proyecto.inventario.events;

import com.proyecto.inventario.dto.PedidoDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PedidoCreatedEvent extends Event<PedidoDto> {

}

