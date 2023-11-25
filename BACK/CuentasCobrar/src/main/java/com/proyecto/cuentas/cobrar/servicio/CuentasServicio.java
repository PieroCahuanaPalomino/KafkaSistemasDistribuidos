package com.proyecto.cuentas.cobrar.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.proyecto.cuentas.cobrar.dto.CuentaCobrarDto;
import com.proyecto.cuentas.cobrar.modelo.CuentaCobrarEntidad;
import com.proyecto.cuentas.cobrar.repositorio.CuentaCobrarRepositorio;

@Service
public class CuentasServicio {
	@Autowired
	private CuentaCobrarRepositorio cobrarRepositorio;
	
	@Autowired
    private ModelMapper modelMapper;
	
	
	public List<CuentaCobrarDto> obtenerListaDeElementos(){
		List<CuentaCobrarEntidad> cuentaCobrarEntidad=cobrarRepositorio.findAll();
		List<CuentaCobrarDto> cuentasCobrarDtos = cuentaCobrarEntidad.stream()
	            .map(entidad -> modelMapper.map(entidad, CuentaCobrarDto.class))
	            .collect(Collectors.toList());
		return cuentasCobrarDtos;
	}
}
