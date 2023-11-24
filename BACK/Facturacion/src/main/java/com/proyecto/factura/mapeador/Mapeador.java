package com.proyecto.factura.mapeador;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.proyecto.factura.dto.FacturaRequestDto;
import com.proyecto.factura.dto.ItemFacturaDto;
import com.proyecto.factura.modelo.FacturaEntidad;
import com.proyecto.factura.modelo.ItemFacturaEntidad;

@Component
public class Mapeador {

	@Autowired
    private ModelMapper modelMapper;
	
    //FACTURA DE ENTIDAD A DTO
    public  FacturaEntidad mapFacturaDtoToEntity(FacturaRequestDto facturaDto) {
        return modelMapper.map(facturaDto, FacturaEntidad.class);
    }

    //ITEM DE ENTIDAD A DTO
    public ItemFacturaEntidad mapItemFacturaDtoToEntity(ItemFacturaDto itemFacturaDto) {
        return modelMapper.map(itemFacturaDto, ItemFacturaEntidad.class);
    }

    //FACTURA DE DTO A ENTIDAD
    public FacturaRequestDto mapFacturaEntityToDto(FacturaEntidad factura) {
        return modelMapper.map(factura, FacturaRequestDto.class);
    }

    //ITEM DE DTO A ENTIDAD
    public ItemFacturaDto mapItemFacturaEntityToDto(ItemFacturaEntidad itemFactura) {
        return modelMapper.map(itemFactura, ItemFacturaDto.class);
    }
}