package com.proyecto.procesamiento.servicio;

import org.springframework.stereotype.Service;

import com.proyecto.procesamiento.dto.PedidoDto;

@Service
public class PedidoService {
	
	private final PedidoEventsService customerEventsService;

	public PedidoService(PedidoEventsService customerEventsService) {
		super();
		this.customerEventsService = customerEventsService;
	}

	public PedidoDto save(PedidoDto customer) {
		System.out.println("Received " + customer);
		this.customerEventsService.publish(customer);
		return customer;
		
	}

}