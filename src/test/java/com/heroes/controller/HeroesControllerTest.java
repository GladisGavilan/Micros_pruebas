package com.heroes.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.heroes.dao.HeroesRepository;
import com.heroes.dto.HeroeRequestInput;
import com.heroes.entity.Heroe;
import com.heroes.exceptions.HeroesExceptions;
import com.heroes.service.HeroesServiceImpl;
import com.heroes.util.HeroesUtil;

@ExtendWith(MockitoExtension.class)
public class HeroesControllerTest {
	@Mock
    private HeroesRepository heroesRepository;
	@Mock
	private HeroesUtil heroesUtil;
	@Mock
    private HeroesServiceImpl heroeService;
	
	@InjectMocks
	private HeroesController heroesController;
	
	@Test
    public void testVerListaHeroes() throws ParseException {

		List<Heroe> expectedHeroes = getHeroesList();
        Mockito.when(heroeService.getAllHeroes()).thenReturn(expectedHeroes);

        ResponseEntity<List<Heroe>> response = heroesController.verListaHeroes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }
	
	@Test
    public void testVerHeroe_WhenHeroeExists_ReturnsOkResponse() throws ParseException, HeroesExceptions {
        Integer validHeroeId = 1;
        
        Heroe expectedHeroe = getOneHeroe();
        Mockito.when(heroeService.getHeroeById(validHeroeId)).thenReturn(expectedHeroe);

        ResponseEntity<Heroe> response = heroesController.verHeroe(validHeroeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHeroe, response.getBody());
    }
	
	@Test
    public void testNuevoHeroe_WhenHeroeIsSaved_ReturnsOkResponse() throws HeroesExceptions, ParseException {
        HeroeRequestInput validInput = new HeroeRequestInput();
        validInput.setFechaCreacion("18/12/2001");
        validInput.setNombre("Black Panther");
        
        Heroe expectedHeroe = getOtherHeroe();
        Mockito.when(heroeService.saveNewHeroe(validInput)).thenReturn(expectedHeroe);
        
        ResponseEntity<Heroe> response = heroesController.nuevoHeroe(validInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHeroe, response.getBody());
    }
	
	@Test
    public void testVerHeroesPorNombre_WhenNameExists_ReturnsOkResponse() throws ParseException, HeroesExceptions {
        String validName = "oi";
        
        List<Heroe> expectedHeroes = getHeroesList()
        		.stream()
        		.filter(heroe -> heroe.getNombre().contains(validName))
        		.collect(Collectors.toList());
        Mockito.when(heroeService.getHeroesByName(validName)).thenReturn(expectedHeroes);
        
        ResponseEntity<List<Heroe>> response = heroesController.verHeroesPorNombre(validName);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHeroes, response.getBody());
    }
	
	@Test
    public void testEliminaHeroe_WhenHeroExists_Returns_Ok() {
		Integer idHeroe = 1;
        
        ResponseEntity<Void> response = heroesController.eliminaHeroePorId(idHeroe);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
	
	@Test
    public void testModificaHeroe_WhenHeroeIsUpdated_ReturnsOkResponse() throws HeroesExceptions, ParseException {
        Integer validHeroeId = 8;
        HeroeRequestInput validInput = new HeroeRequestInput();
        validInput.setFechaCreacion("18/12/2001");
        validInput.setNombre("Pink Panther");
        
        Heroe expectedHeroe = getOtherHeroe();
        expectedHeroe.setNombre(validInput.getNombre());
        
        Mockito.when(heroeService.updateHeroe(validHeroeId, validInput)).thenReturn(expectedHeroe);
        
        ResponseEntity<Heroe> response = heroesController.modificaHeroe(validHeroeId, validInput);
        
        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHeroe, response.getBody());
    }
	
	private List<Heroe> getHeroesList() throws ParseException{
    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
    	Date fecha1 = formato.parse("23/11/1956");
    	Heroe h1 = new Heroe();
    	h1.setFechaCreacion(fecha1);
    	h1.setNombre("Heroe 1");
    	h1.setIdHeroe(1);
    	
    	Date fecha2 = formato.parse("06/01/1980");
    	Heroe h2 = new Heroe();
    	h2.setFechaCreacion(fecha2);
    	h2.setNombre("Heroina 2");
      	h1.setIdHeroe(2);
      	
    	Date fecha3 = formato.parse("25/07/1949");
    	Heroe h3 = new Heroe();
    	h3.setFechaCreacion(fecha3);
    	h3.setNombre("Heroe 3");
      	h1.setIdHeroe(3);
    	
    	return Arrays.asList(h1, h2, h3);
    }
    
    private Heroe getOneHeroe() throws ParseException{
    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
    	Date fecha1 = formato.parse("23/04/1961");
    	Heroe h1 = new Heroe();
    	h1.setFechaCreacion(fecha1);
    	h1.setNombre("Mujer Maravilla");
    	h1.setIdHeroe(5);
   	
    	return h1;
    }
    
    private Heroe getOtherHeroe() throws ParseException{
    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
    	Date fecha1 = formato.parse("18/12/2001");
    	Heroe h1 = new Heroe();
    	h1.setFechaCreacion(fecha1);
    	h1.setNombre("Black Panther");
    	h1.setIdHeroe(8);
   	
    	return h1;
    }
}
