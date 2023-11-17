package com.proyecto.inventario.service;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.inventario.dto.ArticuloDto;
import com.proyecto.inventario.dto.PedidoDto;
import com.proyecto.inventario.events.Event;
import com.proyecto.inventario.events.PedidoCreatedEvent;
//import com.proyecto.inventario.mapper.ItemMapper;
import com.proyecto.inventario.model.ArticuloEntity;
import com.proyecto.inventario.repository.ArticuloRepository;
//import com.proyecto.inventario.repository.ItemRepository;
//import com.proyecto.inventario.repository.PedidoRepository;
@Service
public class KafkaConsumerService {
    @Autowired
    private ArticuloRepository articuloRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "customers", groupId = "grupo1")
    public void consumeMessage(String message) {
        try {
            String codigoBuscar;
            int cantidadPedido,cantidadDisponible;
            
        	PedidoCreatedEvent messageObject = objectMapper.readValue(message, PedidoCreatedEvent.class);
            PedidoDto pedidoDto = messageObject.getData();
            logger.info("Mensaje DTO: {}", pedidoDto);
            logger.info("LIST DTO: {}", pedidoDto.getListaArticulos());

           
            
            ArticuloEntity articuloEntity = new ArticuloEntity();

        	logger.info("-------------------------");

        	if(pedidoDto != null){
                for (int i = 0; i < pedidoDto.getListaArticulos().size(); i++) {
                    codigoBuscar = pedidoDto.getListaArticulos().get(i).getCodigoArticulo();
                	articuloEntity = articuloRepository.findByCodigoArticulo(codigoBuscar);
                	logger.info("FOR");
                	logger.info("entity: {}", articuloEntity);
                	logger.info("codigo: {}", codigoBuscar);
                    if (articuloEntity == null) {
                        logger.info("NO HAY {}", codigoBuscar);                    	
                    }else {
                        logger.info("SI HAY {}", codigoBuscar);
                        cantidadPedido = pedidoDto.getListaArticulos().get(i).getCantidadPedido();
                        cantidadDisponible = articuloEntity.getCantidadDisponible();
                        if (cantidadDisponible >= cantidadPedido) {
                            logger.info("SI HAY SUFICIENTE CANTIDAD");
                            // Aquí puedes realizar la lógica de la venta
                            
                        } else {
                            logger.info("NO HAY SUFICIENTE CANTIDAD");
                            // Puedes manejar la lógica cuando no hay suficiente cantidad disponible
                            // por ejemplo, puedes marcar el artículo como no disponible para la venta
                        }
                    }
                }
            }
            

        } catch (Exception e) {
            logger.error("Error al deserializar el mensaje: {}", e.getMessage(), e);
        }
    }
}
