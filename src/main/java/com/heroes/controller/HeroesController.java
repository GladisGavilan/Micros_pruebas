package com.heroes.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heroes.contadorejecuciones.ContadorEjecucion;
import com.heroes.dto.HeroeRequestInput;
import com.heroes.entity.Heroe;
import com.heroes.exceptions.HeroesExceptions;
import com.heroes.service.HeroesService;

import lombok.extern.slf4j.Slf4j;


@RestController 
@Slf4j
//@Api(value = "Heroes",  produces = "application/json")
@RequestMapping(value = "heroesOperations", produces = MediaType.APPLICATION_JSON_VALUE)
public class HeroesController {
	
	@Autowired
	private HeroesService heroesService;
//	@ApiOperation(value = "Lista todos los heroes", notes = "Lista todos los heroes")
//	    @ApiResponses({
//            @ApiResponse(code = 200, message = "Listado emitido de forma exitosa")
//    })	
	@GetMapping(value="lista", produces=MediaType.APPLICATION_JSON_VALUE)
	@ContadorEjecucion
	public ResponseEntity<List<Heroe>> verListaHeroes()
	{
		return ResponseEntity.ok(heroesService.getAllHeroes());
	}

//	@ApiOperation(value = "Lista un heroe", notes = "Lista heroe con el id recibido")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Lista el heroe de forma exitosa"),
//		@ApiResponse(code = 204, message = "No existe heroe para el id ingresado"),
//		@ApiResponse(code = 404, message = "Debe ingresar un id de hereo válido")
//	})	
	@GetMapping(value="verHeroe", produces=MediaType.APPLICATION_JSON_VALUE)
	@ContadorEjecucion
	public ResponseEntity<Heroe> verHeroe(Integer heroeId)
	{
		try {
			return ResponseEntity.ok(heroesService.getHeroeById(heroeId));
		} catch (HeroesExceptions e) {
			log.info(e.getShortMessage());
			return ResponseEntity.status(e.getHttpStatus()).body(null);
		}
	}
		
//	@ApiOperation(value = "Nuevo Heroe", notes = "Da de alta un nuevo heroe")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Alta realizada de forma exitosa"),
//		@ApiResponse(code = 404, message = "La fecha ingresada no tiene el formato correcto")
//	})
	@PostMapping(value="nuevoHeroe", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ContadorEjecucion
	public ResponseEntity<Heroe> nuevoHeroe(@RequestBody HeroeRequestInput heroeRequest) 
	{
		try {
			return ResponseEntity.ok(heroesService.saveNewHeroe(heroeRequest));
		} catch (HeroesExceptions e) {
			log.info(e.getShortMessage());
			return ResponseEntity.status(e.getHttpStatus()).body(null);
		}
	}

//	@ApiOperation(value = "Lista algunos heroes", notes = "Lista todos los heroes cuyo nombre "
//			+ "contenga el valor informado como parametro")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Listado emitido de forma exitosa"),
//		@ApiResponse(code = 404, message = "La fecha ingresada no tiene el formato correcto")
//	})
	@GetMapping(value="nombreContiene", produces=MediaType.APPLICATION_JSON_VALUE)
	@ContadorEjecucion
	public ResponseEntity<List<Heroe>> verHeroesPorNombre(String name)
	{
		try {
			return ResponseEntity.ok(heroesService.getHeroesByName(name));
		} catch (HeroesExceptions e) {
			log.info(e.getShortMessage());
			return ResponseEntity.status(e.getHttpStatus()).body(null);
		}
	}
	
//	@ApiOperation(value = "Elimina heroe según id", notes = "Elimina el heroe cuyo id se recibe como parámetro")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Heroe eliminado de forma exitosa"),
//		@ApiResponse(code = 204, message = "No existe heroe para el id ingresado")
//	})
	@DeleteMapping(value="eliminaHeroe")
	@ContadorEjecucion
 	public ResponseEntity<Void> eliminaHeroePorId(Integer idHeroe)
	{
		try {
			heroesService.deleteHeroe(idHeroe);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (HeroesExceptions e) {
			log.info(e.getShortMessage());
			return ResponseEntity.status(e.getHttpStatus()).body(null);
		}
	}
	
//	@ApiOperation(value = "Modifica heroe según id", notes = "Modifica el heroe cuyo id se recibe como parámetro"
//			+ ", con los datos informados")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "Heroe modificado de forma exitosa"),
//		@ApiResponse(code = 404, message = "La fecha ingresada no tiene el formato correcto")
//	})
	@PutMapping(value="modifica")
	@ContadorEjecucion
	public ResponseEntity<Heroe> modificaHeroe(Integer idHeroe, @RequestBody HeroeRequestInput heroeRequest)
	{
		try {
			return ResponseEntity.ok(heroesService.updateHeroe(idHeroe, heroeRequest));
		} catch (HeroesExceptions e) {
			log.info(e.getShortMessage());
			return ResponseEntity.status(e.getHttpStatus()).body(null);
		}
	}
}