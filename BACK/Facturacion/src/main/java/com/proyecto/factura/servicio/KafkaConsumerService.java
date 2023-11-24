package com.proyecto.factura.servicio;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.protocol.Protocol.ProtocolEventListener.EventType;
import com.proyecto.factura.dto.FacturaRequestDto;
import com.proyecto.factura.dto.FacturaResponseDto;
import com.proyecto.factura.dto.ItemFacturaDto;
import com.proyecto.factura.dto.PedidoDto;
import com.proyecto.factura.events.PedidoCreatedEvent;
import com.proyecto.factura.mapeador.Mapeador;
import com.proyecto.factura.modelo.FacturaEntidad;
import com.proyecto.factura.modelo.ItemFacturaEntidad;
import com.proyecto.factura.repositorio.FacturaRepositorio;
//import com.proyecto.inventario.mapper.ItemMapper;
//import com.proyecto.inventario.repository.ItemRepository;
//import com.proyecto.inventario.repository.PedidoRepository;
@Service
public class KafkaConsumerService {
	String mensaje;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private FacturaRepositorio facturaRepositorio;
	
	@Autowired
    private ModelMapper mapeador;
	
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public static String generarCodigo() {
        // Caracteres permitidos para el código
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Longitud del código
        int longitudCodigo = 9;

        // Objeto Random para generar valores aleatorios
        Random random = new Random();

        // StringBuilder para construir el código
        StringBuilder codigoBuilder = new StringBuilder();

        // Generar el código
        for (int i = 0; i < longitudCodigo; i++) {
            int indice = random.nextInt(caracteres.length());
            char caracter = caracteres.charAt(indice);
            codigoBuilder.append(caracter);
        }

        return codigoBuilder.toString();
    }

    @KafkaListener(topics = "customers_bill", groupId = "grupo1")
    public void consumeMessage(String message) {
        try {
            // Deserializa el mensaje JSON a un objeto de tipo PedidoCreatedEvent
        	PedidoCreatedEvent messageObject = objectMapper.readValue(message, PedidoCreatedEvent.class);
        	
            // Accede a la propiedad "data" en el objeto PedidoCreatedEvent
            PedidoDto pedidoDto = messageObject.getData();
            
            // Realiza las operaciones con el objeto deserializado
            System.out.println("Mensaje deserializado: " + messageObject);
            System.out.println("Mensaje DTO: " + pedidoDto);
            System.out.println("Mensaje DTO: " + messageObject.getDate());
            mensaje=messageObject.getType().toString();

            FacturaRequestDto facturaDto=new FacturaRequestDto();
            facturaDto.setNombre(pedidoDto.getComprador().getNombre());
            facturaDto.setDniRuc(pedidoDto.getComprador().getDniRuc());
            facturaDto.setFecha(messageObject.getDate());
            //FALTA L TOTAL
            List<String> listaDinamica = new ArrayList<>();
            double mult1;
            BigDecimal mult2;
            BigDecimal sum= BigDecimal.ZERO;
            List<ItemFacturaDto>  itemFacturaDtoList=new ArrayList<>();
            for(int i=0;i<pedidoDto.getListaArticulos().size();i++) {
            	ItemFacturaDto itemFacturaDto = new ItemFacturaDto();  // Crear una nueva instancia en cada iteración
            	//itemFacturaDto.setIdArticulo(i);
            	listaDinamica.add(pedidoDto.getListaArticulos().get(i).getCodigoArticulo());
                itemFacturaDto.setCantidad(pedidoDto.getListaArticulos().get(i).getCantidadPedido());
                mult1 = pedidoDto.getListaArticulos().get(i).getCantidadPedido();
                mult2 = pedidoDto.getListaArticulos().get(i).getPrecioUnitario();
                itemFacturaDto.setSubtotal(mult2.multiply(BigDecimal.valueOf(mult1)));
                sum = sum.add(itemFacturaDto.getSubtotal());
                itemFacturaDtoList.add(itemFacturaDto);  // Agregar el objeto a la lista
            }
            facturaDto.setTotalFactura(sum);
            facturaDto.setTotalIgv(facturaDto.getTotalFactura().multiply(new BigDecimal("0.18")));
            
            System.out.println("factura DTO: " + facturaDto.toString());
            
            FacturaEntidad facturaEntidad=mapeador.map(facturaDto, FacturaEntidad.class);
            
            System.out.println("factura ENTIDAD sin items: " + facturaEntidad.toString());
            
            
            System.out.println("Lista ItemFacturaDto: " + itemFacturaDtoList.toString());

            
            List<ItemFacturaEntidad> itemFacturaEntidadList = itemFacturaDtoList.stream()
                    .map(itemDto -> {
                        ItemFacturaEntidad itemEntidad = mapeador.map(itemDto, ItemFacturaEntidad.class);
                        itemEntidad.setFactura(facturaEntidad); // Establecer la referencia inversa
                        return itemEntidad;
                    })
                    .collect(Collectors.toList());
            
            for(int i=0;i<itemFacturaEntidadList.size();i++) {
            	itemFacturaEntidadList.get(i).setIdArticulo(pedidoDto.getListaArticulos().get(i).getIdArticulo());
            	itemFacturaEntidadList.get(i).setDescripcion(pedidoDto.getListaArticulos().get(i).getNombreArticulo());
            	//itemFacturaEntidadList.get(i).
            	 //System.out.println(listaDinamica.get(i));
            }
            
            facturaEntidad.setCodigoCliente(generarCodigo());
            facturaEntidad.setItems(itemFacturaEntidadList);
            
            facturaRepositorio.save(facturaEntidad);
            
            if(messageObject.getType().toString().equals("LISTO_FACTURA")) {
            	FacturaResponseDto facturaResponseDto=new FacturaResponseDto();
            	facturaResponseDto.setIdFactura(facturaEntidad.getIdFactura());
            	facturaResponseDto.setFechaFactura(facturaDto.getFecha());
            	facturaResponseDto.setCodigoCliente(generarCodigo());
            	facturaResponseDto.setDniRuc(facturaDto.getDniRuc());
            	facturaResponseDto.setNombreCliente(facturaEntidad.getNombre());
            	facturaResponseDto.setTotalIgv(facturaDto.getTotalIgv());
            	facturaResponseDto.setTotalFactura(sum);
            	facturaResponseDto.setLista(itemFacturaEntidadList.stream()
            			.map(entidad->mapeador.map(entidad, ItemFacturaDto.class))
            			.collect(Collectors.toList())
            			);
                System.out.println("Mensaje que se enviara a KAFKA: " + facturaResponseDto.toString());   
                
                pedidoService.save(facturaResponseDto);
            }
            
        } catch (Exception e) {
            System.err.println("Error al deserializar el mensaje: " + e.getMessage());
        }
    }
}