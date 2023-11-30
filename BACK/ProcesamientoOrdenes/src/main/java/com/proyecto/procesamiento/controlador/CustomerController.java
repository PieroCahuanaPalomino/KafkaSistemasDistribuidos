package com.proyecto.procesamiento.controlador;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.procesamiento.dto.CompradorDto;
import com.proyecto.procesamiento.dto.PedidoDto;
import com.proyecto.procesamiento.servicio.PedidoService;


@RestController
@RequestMapping("/pedido")
public class CustomerController {
	
	private final PedidoService customerService;

	public CustomerController(PedidoService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@PostMapping
	public <T>T save(@RequestBody PedidoDto customer) {
		return this.customerService.save(customer);
	}
	

}