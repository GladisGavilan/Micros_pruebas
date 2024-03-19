package com.heroes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.heroes.dao.HeroesRepository;
import com.heroes.dto.HeroeRequestInput;
import com.heroes.entity.Heroe;
import com.heroes.exceptions.HeroesExceptions;
import com.heroes.util.HeroesUtil;

@ExtendWith(MockitoExtension.class)
public class HeroesServiceTest {
	@Mock
    private HeroesRepository heroesRepository;
	@Mock
	private HeroesUtil heroesUtil;
	@InjectMocks
    private HeroesServiceImpl heroeService;
	
    @Test
    public void testGetAllHeroes_OK() throws ParseException {
    	List<Heroe> expectedHeroes = getHeroesList();
        Mockito.when(heroesRepository.findAll()).thenReturn(expectedHeroes);
        
        List<Heroe> actualHeroes = heroeService.getAllHeroes();
        
        assertEquals(expectedHeroes, actualHeroes);
    }
    
    @Test
    public void testGetHeroeById_WhenIdNotNull_ReturnsHeroe() throws ParseException, HeroesExceptions {
    	Integer heroeId = 1;
        
        Heroe expectedHeroe = getOneHeroe();
        Mockito.when(heroesRepository.findById(heroeId)).thenReturn(Optional.of(expectedHeroe));
        
        Heroe actualHeroe = heroeService.getHeroeById(heroeId);

        assertEquals(expectedHeroe, actualHeroe);
    }
    
    @Test
    public void testGetHeroeById_WhenIdNull_ThrowsBadRequestException() {
    	Integer heroeId = null;
        
        assertThrows(HeroesExceptions.class, () -> {
        	heroeService.getHeroeById(heroeId);
        });
    }

    @Test
    public void testSaveNewHeroe_Ok() throws HeroesExceptions, ParseException {
        HeroeRequestInput validInput = new HeroeRequestInput();
        validInput.setFechaCreacion("21/02/1970");
        validInput.setNombre("Linterna verde");
        
        Mockito.when(heroesUtil.convertDate(validInput.getFechaCreacion())).thenReturn(new Date());
        Mockito.when(heroesUtil.isValidDate(validInput.getFechaCreacion(), "dd/MM/yyyy")).thenReturn(true);
		
        Heroe savedHeroe = heroeService.saveNewHeroe(validInput);
        
        assertNotNull(savedHeroe);
        assertEquals(savedHeroe.getNombre(), "Linterna verde");
    }
    /*
    @Test
    public void testGetHeroesByName_WhenInputIsValid_() throws ParseException, HeroesExceptions {
    	String validInput = "ina";
        
        List<Heroe> expectedHeroes = getHeroesList().stream().filter(elemento -> elemento.getNombre() == "Heroina 2").toList();
        List<Heroe> expectedHeroesUpperCase = getHeroesList().stream().filter(elemento -> elemento.getNombre() == "HEROINA 2").toList();
        Mockito.when(heroesRepository.findByNombreContaining(validInput)).thenReturn(expectedHeroes);
        Mockito.when(heroesRepository.findByNombreContaining(validInput.toUpperCase())).thenReturn(expectedHeroesUpperCase);

        List<Heroe> actualHeroes = heroeService.getHeroesByName(validInput);
        
        assertEquals(expectedHeroes, actualHeroes);
    }*/
    
    @Test
    public void testGetHeroesByName_WhenInputIsInvalid_ThrowsBadRequestException() {
        assertThrows(HeroesExceptions.class, () -> {
        	heroeService.getHeroesByName(null);
        });
    }
    
    @Test
    public void testDeleteHeroe_WhenHeroeIdIsNotNull_DeletesHeroe() throws HeroesExceptions, ParseException {
        Integer validHeroeId = 1;
        
        Mockito.when(heroesRepository.findById(validHeroeId)).thenReturn(Optional.of(getOneHeroe()));
        
        heroeService.deleteHeroe(validHeroeId);
        
        verify(heroesRepository, times(1)).deleteById(validHeroeId);
    }
    
    @Test
    public void testDeleteHeroe_WhenHeroeIdIsNull_ThrowsBadRequestException() {
    	assertThrows(HeroesExceptions.class, () -> {
    		heroeService.deleteHeroe(null);
        });
    }

    @Test
    public void testUpdateHeroe_WhenHeroeIdIsNotNullAndInputIsValid_ReturnsUpdatedHeroe() throws HeroesExceptions, ParseException {
        Integer validHeroeId = 5;
        HeroeRequestInput validInput = new HeroeRequestInput();
        validInput.setFechaCreacion("23/04/1961");
        validInput.setNombre("Batman");
        
        Heroe existingHeroe = getOneHeroe();
        Mockito.when(heroesRepository.findById(validHeroeId)).thenReturn(Optional.of(existingHeroe));
        Mockito.when(heroesRepository.save(existingHeroe)).thenReturn(existingHeroe);
        Mockito.when(heroesUtil.isValidDate(validInput.getFechaCreacion(), "dd/MM/yyyy")).thenReturn(true);
        
        Heroe updatedHeroe = heroeService.updateHeroe(validHeroeId, validInput);

        assertNotNull(updatedHeroe);
        assertEquals("Batman", updatedHeroe.getNombre());
     }
    
    @Test
    public void testUpdateHeroe_WhenHeroeIdIsNull_ThrowsBadRequestException() {
        assertThrows(HeroesExceptions.class, () -> {
        	heroeService.updateHeroe(null, new HeroeRequestInput());
        });
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
}
