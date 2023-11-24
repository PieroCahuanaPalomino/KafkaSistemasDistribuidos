package com.proyecto.factura.servicio;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.proyecto.factura.dto.FacturaResponseDto;
import com.proyecto.factura.dto.PedidoDto;
import com.proyecto.factura.events.Event;
import com.proyecto.factura.events.EventType;
import com.proyecto.factura.events.FacturaCreatedEvent;
import com.proyecto.factura.events.PedidoCreatedEvent;

@Component
public class PedidoEventsService {
	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
	@Value("${topic.customer.name:customers_accounts}")
	private String topicCustomer;
	
	
	public void publish(FacturaResponseDto customer) {
		// TODO Auto-generated method stub
		FacturaCreatedEvent created = new FacturaCreatedEvent();
		created.setData(customer);
		created.setId(UUID.randomUUID().toString());
		created.setDate(new Date());
		created.setType(EventType.LISTO_CUENTA_COBRAR);
		
		this.producer.send(topicCustomer, created);
	}

}