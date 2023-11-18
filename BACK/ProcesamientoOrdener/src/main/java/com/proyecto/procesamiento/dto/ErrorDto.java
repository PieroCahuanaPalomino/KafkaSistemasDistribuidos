package com.proyecto.procesamiento.dto;

import org.springframework.context.annotation.Scope;

import com.proyecto.procesamiento.kafka.events.EventType;
import com.proyecto.procesamiento.kafka.events.EventTypeError;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

@Data
public class ErrorDto {
    private EventTypeError type;
}
