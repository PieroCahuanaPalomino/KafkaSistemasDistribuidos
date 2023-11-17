package com.proyecto.inventario.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.proyecto.inventario.dto.ItemDto;
import com.proyecto.inventario.model.ItemEntity;
@Component
public class ItemMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ItemDto mapearDTO(ItemEntity entity) {
    	ItemDto itemDto=modelMapper.map(entity, ItemDto.class);
    	return itemDto;
    }
    
    
    public ItemEntity mapearEntity(ItemDto dto) {
    	ItemEntity itemEntity=modelMapper.map(dto, ItemEntity.class);
    	return itemEntity;
    }
}