package com.heroes.contadorejecuciones;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class CalculaTiempoEjecucion {
	    @Around("@annotation(com.heroes.contadorejecuciones.ContadorEjecucion)")
	    public Object calcularTiempoEjecucion(ProceedingJoinPoint proceso) throws Throwable {

	        long iniciaEjecucion = System.currentTimeMillis();
	        //Luego de capturar el tiempo, se lanza la ejecución
	        Object ejecucion = proceso.proceed();
	        //Una vez finalizada la misma, se toma el tiempo nuevamente para hacer la diferencia       
	        long tiempoEjecucion = System.currentTimeMillis() - iniciaEjecucion;
	        log.info("La ejecución de " + proceso.getSignature() + " a consumido " + tiempoEjecucion +"ms");
	        return ejecucion;
	    }
}
