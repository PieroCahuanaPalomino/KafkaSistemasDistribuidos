package com.proyecto.inventario.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.inventario.dto.ItemDto;
import com.proyecto.inventario.dto.PedidoDto;
import com.proyecto.inventario.events.Event;
import com.proyecto.inventario.events.PedidoCreatedEvent;
import com.proyecto.inventario.mapper.ItemMapper;
import com.proyecto.inventario.model.ItemEntity;
import com.proyecto.inventario.model.PedidoEntity;
//import com.proyecto.inventario.repository.ItemRepository;
//import com.proyecto.inventario.repository.PedidoRepository;
@Service
public class KafkaConsumerService {
	@Autowired
	private ItemMapper itemMapper;
	
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "customers", groupId = "grupo1")
    public void consumeMessage(String message) {
        try {
            // Deserializa el mensaje JSON a un objeto de tipo PedidoCreatedEvent
            PedidoCreatedEvent messageObject = objectMapper.readValue(message, PedidoCreatedEvent.class);
            
            // Accede a la propiedad "data" en el objeto PedidoCreatedEvent
            PedidoDto pedidoDto = messageObject.getData();
            
            // Realiza las operaciones con el objeto deserializado
            System.out.println("Mensaje deserializado: " + messageObject);
            System.out.println("Mensaje DTO: " + pedidoDto);

            
            /*
            do {
            	if(pedidoDto!=null) {
                    System.out.println("FINAL");
                    ItemDto itemDto=pedidoDto.getItemsDto().get(0);
                    System.out.println(itemDto.toString());
                    ItemEntity itemEntity=itemMapper.mapearEntity(itemDto);
                    System.out.println(itemEntity.getCodigo());
                    System.out.println(itemEntity.getNombre());
                    System.out.println(itemEntity.getCantidad());
                    System.out.println(itemEntity.getPedido());
                    
                    break;
            	}
            }while(true);*/

        } catch (Exception e) {
            System.err.println("Error al deserializar el mensaje: " + e.getMessage());
        }
    }
}
