package com.proyecto.procesamiento.servicio;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.proyecto.procesamiento.dto.PedidoDto;
import com.proyecto.procesamiento.kafka.events.Event;
import com.proyecto.procesamiento.kafka.events.EventType;
import com.proyecto.procesamiento.kafka.events.PedidoCreatedEvent;

import java.util.UUID;

@Component
public class PedidoEventsService {
	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
	@Value("${topic.customer.name:customers}")
	private String topicCustomer;
	
	public void publish(PedidoDto customer) {

		PedidoCreatedEvent created = new PedidoCreatedEvent();
		created.setData(customer);
		created.setId(UUID.randomUUID().toString());
		created.setType(EventType.CREATED);
		created.setDate(new Date());

		this.producer.send(topicCustomer, created);
	}
	
	

}