package com.proyecto.procesamiento.kafka.events;

import com.proyecto.procesamiento.dto.PedidoDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegistroCreatedEvent extends Event<String> {

}