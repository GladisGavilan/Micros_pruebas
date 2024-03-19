package com.heroes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class HeroesUtil {
	public boolean isValidDate(String d, String dateFormat) {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
		  try {
		    dtf.parse(d);
		  } catch(DateTimeParseException ex) {
		    return false;
		  }
		  return true;
		}
	
	public Date convertDate(String fecha) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaCreacionFormateada;
		try {
			fechaCreacionFormateada = formato.parse(fecha);
		}
		catch (ParseException e) {
			formato = new SimpleDateFormat("dd-MM-yyyy");
			fechaCreacionFormateada = formato.parse(fecha);
		}
		return fechaCreacionFormateada;
	}
}
