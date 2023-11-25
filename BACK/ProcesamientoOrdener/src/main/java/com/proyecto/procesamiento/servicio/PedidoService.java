package com.proyecto.procesamiento.servicio;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.procesamiento.dto.ErrorDto;
import com.proyecto.procesamiento.dto.PedidoDto;
import com.proyecto.procesamiento.dto.SuccesDto;
import com.proyecto.procesamiento.kafka.events.EventType;
import com.proyecto.procesamiento.kafka.events.EventTypeError;
import com.proyecto.procesamiento.kafka.events.EventTypeSucces;

@Service
public class PedidoService {
	@Autowired
	private KafkaConsumerService consumerService;
	
	private final PedidoEventsService customerEventsService;

	public PedidoService(PedidoEventsService customerEventsService) {
		super();
		this.customerEventsService = customerEventsService;
	}

	public <T>T save(PedidoDto customer) {
		ErrorDto dto=new ErrorDto();
		String mensajeAsignacion;
		
		System.out.println("Received " + customer);
		this.customerEventsService.publish(customer);		
		
		do {
			if(consumerService.mensaje!=null) {
				mensajeAsignacion=consumerService.mensaje;
				break;
			}
			mensajeAsignacion=consumerService.mensajeExito;
		}while(consumerService.mensajeExito==null);
		
		if(consumerService.mensaje != null) {
			
			if(mensajeAsignacion.equals("INEXISTENTE")) {
				dto.setType(EventTypeError.INEXISTENTE);	
				consumerService.mensaje=null;
				return (T) dto;

			}else if (mensajeAsignacion.equals("INSUFICIENTE")) {
				dto.setType(EventTypeError.INSUFICIENTE);						
				consumerService.mensaje=null;
				return (T) dto;
			}else {
				dto.setType(EventTypeError.ERROR);
				return (T) dto;				
			}
			//verificar sino poner dbajo de Received
			//aqui trabajar cuando sea bueno
		}else if(consumerService.mensajeExito !=null && mensajeAsignacion.equals("EXITO")){
				SuccesDto succesDto=new SuccesDto();
				succesDto.setType(EventTypeSucces.EXITO);
				return (T) succesDto;				
		}else {
			dto.setType(EventTypeError.ERROR);
			return (T) dto;
		}
		

	}
	
	
}