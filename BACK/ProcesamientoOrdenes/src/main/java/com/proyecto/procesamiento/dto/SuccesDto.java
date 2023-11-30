package com.proyecto.procesamiento.dto;

import com.proyecto.procesamiento.kafka.events.EventTypeSucces;

import lombok.Data;

@Data
public class SuccesDto {
	private EventTypeSucces type;
}
