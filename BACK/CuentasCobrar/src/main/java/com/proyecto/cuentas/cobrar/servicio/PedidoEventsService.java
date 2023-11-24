package com.proyecto.cuentas.cobrar.servicio;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.proyecto.cuentas.cobrar.dto.CuentaCobrarDto;
import com.proyecto.cuentas.cobrar.event.Event;
import com.proyecto.cuentas.cobrar.event.EventType;
import com.proyecto.cuentas.cobrar.event.RegistroCreatedEvent;


@Component
public class PedidoEventsService {
	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
	@Value("${topic.customer.name:customers_register}")
	private String topicCustomer;
	
	
	public void publish(String customer) {
		// TODO Auto-generated method stub
		RegistroCreatedEvent created = new RegistroCreatedEvent();
		created.setData(customer.toString());
		created.setId(UUID.randomUUID().toString());
		created.setDate(new Date());
		created.setType(EventType.REGISTRADO);
		
		this.producer.send(topicCustomer, created);
	}

}