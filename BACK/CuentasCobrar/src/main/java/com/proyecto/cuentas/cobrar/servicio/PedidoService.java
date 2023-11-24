package com.proyecto.cuentas.cobrar.servicio;

import org.springframework.stereotype.Service;

import com.proyecto.cuentas.cobrar.dto.CuentaCobrarDto;


@Service
public class PedidoService {
	
	
	private final PedidoEventsService customerEventsService;

	public PedidoService(PedidoEventsService customerEventsService) {
		super();
		this.customerEventsService = customerEventsService;
	}

	public String save(String customer) {
		System.out.println("Received " + customer);
		this.customerEventsService.publish(customer);
		return customer;
		
	}

}