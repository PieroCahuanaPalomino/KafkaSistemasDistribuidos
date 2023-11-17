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
		
		@Value("${topic.customer.name:customers_reservation}")
		private String topicCustomer;
		
		@Value("${topic.customer.name:customers_error}")
		private String topicCustomerError;
		
		public void publish(PedidoDto customer,String estadoKafka) {

			PedidoCreatedEvent created = new PedidoCreatedEvent();
			created.setData(customer);
			created.setId(UUID.randomUUID().toString());
			created.setDate(new Date());

			if(estadoKafka.equals(EventType.ENVIADO.toString())) {
				created.setType(EventType.ENVIADO);
				this.producer.send(topicCustomer, created);
			}else if(estadoKafka.equals(EventType.INEXISTENTE.toString())){
				created.setType(EventType.INEXISTENTE);
				this.producer.send(topicCustomerError, created);
			}else if(estadoKafka.equals(EventType.INSUFICIENTE.toString())) {
				created.setType(EventType.INSUFICIENTE);
				this.producer.send(topicCustomerError, created);
			}

		}

	
}
