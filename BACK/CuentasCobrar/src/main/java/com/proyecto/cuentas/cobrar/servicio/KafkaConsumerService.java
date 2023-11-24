package com.proyecto.cuentas.cobrar.servicio;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.cuentas.cobrar.dto.CuentaCobrarDto;
import com.proyecto.cuentas.cobrar.dto.FacturaDto;
import com.proyecto.cuentas.cobrar.event.FacturaCreatedEvent;
import com.proyecto.cuentas.cobrar.modelo.CuentaCobrarEntidad;
import com.proyecto.cuentas.cobrar.repositorio.CuentaCobrarRepositorio;

@Service
public class KafkaConsumerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
	private PedidoService pedidoService;
    
    @Autowired
    private CuentaCobrarRepositorio cobrarRepositorio; 
    
	@Autowired
    private ModelMapper mapeador;
    
    @KafkaListener(topics = "customers_accounts", groupId = "grupo1")
    public void consumeMessage(String message) {
    	try {
    		FacturaCreatedEvent messageObject = objectMapper.readValue(message, FacturaCreatedEvent.class);
    		FacturaDto facturaResponseDto= messageObject.getData();
    		
    		// Realiza las operaciones con el objeto deserializado
            System.out.println("Mensaje deserializado: " + messageObject);
            System.out.println("Mensaje DTO: " + facturaResponseDto);

    		CuentaCobrarDto cobrarDto=mapeador.map(facturaResponseDto, CuentaCobrarDto.class);
    		System.out.println(cobrarDto);
    		
    		CuentaCobrarEntidad cobrarEntidad=mapeador.map(cobrarDto, CuentaCobrarEntidad.class);
    		cobrarEntidad.setEstadoRegistro(false);
    		cobrarEntidad.setFechaCobro(null);
    		
    		
    		cobrarRepositorio.save(cobrarEntidad);
    		
    		System.out.println(cobrarEntidad);
    		
    		pedidoService.save("EXITO");
    		
    	}catch(Exception ex) {
            System.err.println("Error al deserializar el mensaje: " + ex.getMessage());
    	}
    }

}
