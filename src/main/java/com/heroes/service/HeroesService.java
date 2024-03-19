package com.heroes.service;


import java.util.List;

import com.heroes.dto.HeroeRequestInput;
import com.heroes.entity.Heroe;
import com.heroes.exceptions.HeroesExceptions;

public interface HeroesService {
	
	public List<Heroe> getAllHeroes();
	
	public Heroe getHeroeById(Integer heroeId) throws HeroesExceptions;
	
	public Heroe saveNewHeroe(HeroeRequestInput heroeRequest) throws HeroesExceptions;
	
	public List<Heroe> getHeroesByName(String input) throws HeroesExceptions;
	
	public void deleteHeroe(Integer heroeId) throws HeroesExceptions;
	
	public Heroe updateHeroe(Integer heroeId, HeroeRequestInput input) throws HeroesExceptions; 

}
