package com.proyecto.factura.events;

import com.proyecto.factura.dto.PedidoDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PedidoCreatedEvent extends Event<PedidoDto> {

}

