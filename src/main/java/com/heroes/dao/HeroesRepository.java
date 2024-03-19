package com.heroes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heroes.entity.Heroe;

@Repository
public interface HeroesRepository extends JpaRepository<Heroe, Integer>{
		
	public List<Heroe> findByNombreContaining(String name);
}
