package com.proyecto.procesamiento.servicio;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.procesamiento.dto.ErrorDto;
import com.proyecto.procesamiento.dto.PedidoDto;
import com.proyecto.procesamiento.kafka.events.PedidoCreatedEvent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class KafkaConsumerService {
	String mensaje;
	
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "customers_error", groupId = "grupo1")
    public void consumeMessage(String message) {
        try {
            PedidoCreatedEvent messageObject = objectMapper.readValue(message, PedidoCreatedEvent.class);
            PedidoDto pedidoDto = messageObject.getData();
            System.out.println("Mensaje deserializado: " + messageObject);
            System.out.println("Mensaje DTO: " + pedidoDto);
            System.out.println("Mensaje TYPE: " + messageObject.getType());
            mensaje=messageObject.getType().toString();
        } catch (Exception e) {
            System.err.println("Error al deserializar el mensaje: " + e.getMessage());
        }
    }
}

