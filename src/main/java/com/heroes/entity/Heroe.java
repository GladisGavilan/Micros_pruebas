package com.heroes.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="heroes")
public class Heroe implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idHeroe;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="FECHACREACION")
	private Date fechaCreacion;
	
	public Heroe() {
	}
}
