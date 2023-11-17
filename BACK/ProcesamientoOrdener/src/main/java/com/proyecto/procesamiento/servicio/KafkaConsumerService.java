package com.proyecto.procesamiento.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.procesamiento.dto.PedidoDto;
import com.proyecto.procesamiento.kafka.events.PedidoCreatedEvent;

@Service
public class KafkaConsumerService {
	
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "customers_error", groupId = "grupo1")
    public void consumeMessage(String message) {
        try {
            // Deserializa el mensaje JSON a un objeto de tipo PedidoCreatedEvent
            PedidoCreatedEvent messageObject = objectMapper.readValue(message, PedidoCreatedEvent.class);
            
            // Accede a la propiedad "data" en el objeto PedidoCreatedEvent
            PedidoDto pedidoDto = messageObject.getData();
            
            // Realiza las operaciones con el objeto deserializado
            System.out.println("Mensaje deserializado: " + messageObject);
            System.out.println("Mensaje DTO: " + pedidoDto);

            
            

        } catch (Exception e) {
            System.err.println("Error al deserializar el mensaje: " + e.getMessage());
        }
    }
}