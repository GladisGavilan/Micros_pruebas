package com.heroes.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.heroes.dao.HeroesRepository;
import com.heroes.dto.HeroeRequestInput;
import com.heroes.entity.Heroe;
import com.heroes.exceptions.HeroesExceptions;
import com.heroes.util.HeroesUtil;



@Service
public class HeroesServiceImpl implements HeroesService {
	@Autowired
	private HeroesRepository heroRepository;
	
	@Autowired
	private HeroesUtil heroesUtil;
	
	@Cacheable("getAllHeroes")
	public List<Heroe> getAllHeroes(){
		return (List<Heroe>)heroRepository.findAll();
	}
	
	@Cacheable("getHeroeById")
	public Heroe getHeroeById(Integer heroeId) throws HeroesExceptions{
		Heroe heroeResul = new Heroe();
		if (heroeId != null) {
			try {
				heroeResul = heroRepository.findById(heroeId).get();
			}
			catch(NoSuchElementException e) {
				throw new HeroesExceptions(HttpStatus.NO_CONTENT, "No existe heroe para el id ingresado");
			}
		}
		else {
			throw new HeroesExceptions(HttpStatus.BAD_REQUEST, "Debe ingresar un id de hereo válido");
		}
		return heroeResul;
	}
	
	@CacheEvict("getAllHeroes")
	public Heroe saveNewHeroe(HeroeRequestInput heroeRequest) throws HeroesExceptions  {
		Heroe heroeToSave = new Heroe();
		if (validateHeroeRequestInput(heroeRequest)) {
			try {
				heroeToSave.setFechaCreacion(heroesUtil.convertDate(heroeRequest.getFechaCreacion()));
			} catch (ParseException e) {
				 throw new HeroesExceptions(HttpStatus.BAD_REQUEST, "La fecha ingresada no tiene el formato correcto");
			}
			heroeToSave.setNombre(heroeRequest.getNombre());
			heroRepository.save(heroeToSave);
		}
		return heroeToSave;
	}
	
	public List<Heroe> getHeroesByName(String input) throws HeroesExceptions {
		List<Heroe> heroes =  new ArrayList<>();
		if (input == null || input.isEmpty() ) {
			throw new HeroesExceptions(HttpStatus.BAD_REQUEST, "Debe ingresar valor para la búsqueda");
		}
		else {
			heroes = heroRepository.findByNombreContaining(input);
			heroes.addAll(heroRepository.findByNombreContaining(input.toUpperCase()));
		}
		return heroes;
	}
	
	@CacheEvict("getAllHeroes")
	public void deleteHeroe(Integer heroeId) throws HeroesExceptions {
		if (heroeId != null) {
			heroRepository.findById(heroeId).orElseThrow((() -> new HeroesExceptions(HttpStatus.NO_CONTENT, "No existe heroe para el id ingresado")));
			heroRepository.deleteById(heroeId);
		}
		else {
			throw new HeroesExceptions(HttpStatus.BAD_REQUEST, "Debe ingresar un id de hereo válido");
		}
	 }
	@CacheEvict("getAllHeroes")
	public Heroe updateHeroe(Integer heroeId, HeroeRequestInput input) throws HeroesExceptions{ 
		Heroe heroeExistente = new Heroe();
		if (heroeId != null) {
			heroeExistente = heroRepository.findById(heroeId).orElseThrow((() -> new HeroesExceptions(HttpStatus.NO_CONTENT, "No existe heroe para el id ingresado")));
					
		}
		if (validateHeroeRequestInput(input)) {
			try {
				heroeExistente.setFechaCreacion(heroesUtil.convertDate(input.getFechaCreacion()));
			} catch (ParseException e) {
				 throw new HeroesExceptions(HttpStatus.BAD_REQUEST, "La fecha ingresada no tiene el formato correcto");
			}
			heroeExistente.setNombre(input.getNombre());
			heroRepository.save(heroeExistente);
		}
		else {
			throw new HeroesExceptions(HttpStatus.BAD_REQUEST, "La fecha ingresada no tiene el formato correcto");
		}
			
        return heroeExistente;
	}
	
	private boolean validateHeroeRequestInput (HeroeRequestInput input) 
	{
		if (input.getNombre() == null ||  input.getFechaCreacion() == null) {
			return false;
		}
		if (input.getNombre().isEmpty()) {
			return false;
		}
		return (heroesUtil.isValidDate(input.getFechaCreacion(), "dd/MM/yyyy")
				|| heroesUtil.isValidDate(input.getFechaCreacion(), "dd-MM-yyyy"));
		
	}


}
