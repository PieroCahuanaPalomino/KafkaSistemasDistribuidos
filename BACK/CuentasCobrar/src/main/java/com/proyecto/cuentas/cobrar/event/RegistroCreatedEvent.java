package com.proyecto.cuentas.cobrar.event;

import com.proyecto.cuentas.cobrar.dto.CuentaCobrarDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegistroCreatedEvent extends Event<String>{

}
