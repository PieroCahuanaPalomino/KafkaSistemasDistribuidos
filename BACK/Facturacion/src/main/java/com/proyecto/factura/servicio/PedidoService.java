package com.proyecto.factura.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.proyecto.factura.dto.FacturaRequestDto;
import com.proyecto.factura.dto.FacturaResponseDto;
import com.proyecto.factura.dto.PedidoDto;

@Service
public class PedidoService {
	
	
	private final PedidoEventsService customerEventsService;

	public PedidoService(PedidoEventsService customerEventsService) {
		super();
		this.customerEventsService = customerEventsService;
	}

	public FacturaResponseDto save(FacturaResponseDto customer) {
		System.out.println("Received " + customer);
		this.customerEventsService.publish(customer);
		return customer;
		
	}

}