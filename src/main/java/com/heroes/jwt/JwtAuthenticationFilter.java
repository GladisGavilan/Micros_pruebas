package com.heroes.jwt;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.heroes.util.Constants;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter{

    private final JwtService jwtService;
    
    public JwtAuthenticationFilter(@Lazy AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }
  
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
    		HttpServletResponse response, 
    		FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

    	final String token = getTokenFromRequest(request);

    	if (request.getRequestURI().contains("swagger-ui") 
        		|| request.getRequestURI().contains("v2/api-docs")
        			|| request.getRequestURI().contains("/auth/")
        				|| token ==  null) {
        	filterChain.doFilter(request, response);
            return;
        }
        else {
        	UsernamePasswordAuthenticationToken usuarioToken = jwtService.getAuthenticationFromRequest(request);
	      
        	if (usuarioToken!=null ){
	
	                SecurityContextHolder.getContext().setAuthentication(usuarioToken);
	            }
        	filterChain.doFilter(request, response);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(Constants.ENCABEZADO);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith(Constants.PREFIJO_TOKEN))
        {
            return authHeader.substring(7);
        }
        return null;
    }
  
}


    