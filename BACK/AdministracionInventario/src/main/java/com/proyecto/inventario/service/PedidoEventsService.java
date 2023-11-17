package com.proyecto.inventario.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.proyecto.inventario.dto.PedidoDto;
import com.proyecto.inventario.events.Event;
import com.proyecto.inventario.events.EventType;
import com.proyecto.inventario.events.PedidoCreatedEvent;




@Component
public class PedidoEventsService {
		
		@Autowired
		private KafkaTemplate<String, Event<?>> producer;
		
		@Value("${topic.customer.name:customers_reserva}")
		private String topicCustomer;
		
		public void publish(PedidoDto customer) {

			PedidoCreatedEvent created = new PedidoCreatedEvent();
			created.setData(customer);
			created.setId(UUID.randomUUID().toString());
			created.setType(EventType.ENVIADO);
			created.setDate(new Date());

			this.producer.send(topicCustomer, created);
		}

	
}
