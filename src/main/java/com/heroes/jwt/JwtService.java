package com.heroes.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.heroes.util.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    public String getToken(Authentication authentication) {
        return Jwts
            .builder()
            .setIssuedAt(new Date(System.currentTimeMillis())) //Fecha creacion token
            .setSubject(authentication.getName()) //nombre del usuario
            .claim("authorities", authentication.getAuthorities().stream() //lista de authorizaciones del usuario
            		.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .setExpiration(new Date(System.currentTimeMillis() + Constants.TIEMPO_VIDA))
            .signWith(getKey()) //indicamos clave y tipo de codificacion
            .compact(); //generamos el string con el token
    }

    private Key getKey() {
       return Keys.hmacShaKeyFor(Constants.SECRET_KEY.getBytes());
    }
    
    public UsernamePasswordAuthenticationToken getAuthenticationFromRequest(HttpServletRequest request) {
    	String token = request.getHeader(Constants.ENCABEZADO); //recupero token
    	if (token != null) {
    		Claims permisos = Jwts
    		.parserBuilder()
    		.setSigningKey(Constants.SECRET_KEY.getBytes())
    		.build()
    		.parseClaimsJws(token.replace(Constants.PREFIJO_TOKEN, ""))
    		.getBody();
    		String user = permisos.getSubject();
    		List<String> authorities = (List<String>) permisos.get("authorities");
    		if (user != null) {
    			return new UsernamePasswordAuthenticationToken(user, null, 
    					authorities.stream()
    					.map(SimpleGrantedAuthority::new)
    					.collect(Collectors.toList()));
    		}
    	}
    	return null;
    }
    
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
    
}