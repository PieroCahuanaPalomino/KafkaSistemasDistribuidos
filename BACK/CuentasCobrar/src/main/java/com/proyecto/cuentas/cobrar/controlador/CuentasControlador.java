package com.proyecto.cuentas.cobrar.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.cuentas.cobrar.dto.CuentaCobrarDto;
import com.proyecto.cuentas.cobrar.servicio.CuentasServicio;

@RestController
@RequestMapping("/cuentas")
public class CuentasControlador {
	
	@Autowired
	private  CuentasServicio cuentasServicio ;

	
	 @GetMapping
	    public ResponseEntity<List<CuentaCobrarDto>> obtenerElementos() {
		 List<CuentaCobrarDto> lista=cuentasServicio.obtenerListaDeElementos();
		 return new ResponseEntity<>(lista, HttpStatus.OK);
	    }


}