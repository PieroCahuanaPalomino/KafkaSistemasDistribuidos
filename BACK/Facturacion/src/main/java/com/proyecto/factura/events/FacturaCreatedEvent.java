package com.proyecto.factura.events;

import com.proyecto.factura.dto.FacturaResponseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FacturaCreatedEvent extends Event<FacturaResponseDto> {

}