package com.heroes.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.heroes.auth.user.User;
import com.heroes.auth.user.UserRepository;
import com.heroes.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final InMemoryUserDetailsManager userDetails;
    private final PasswordEncoder bCryptPasswordEncoder;

    public LoginResponse login(LoginRequest request) throws Exception {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
			String token=jwtService.getToken(authentication);
		    return LoginResponse.builder()
		        .token(token)
		        .build();
    }
	
	public Integer register(LoginRequest request) {
		User usuario = new User();
		usuario.setUsername(request.getUser());
		usuario.setRol("USER");
		usuario.setPassword(request.getPassword());
		Integer randomNumber = (int)(Math.random() * (1000 - 0 + 1) + 0);
		usuario.setId(randomNumber);
		userRepository.save(usuario);
		userDetails.createUser(mapFromUserToUserDetail(usuario));
		return usuario.getId();
	}
		 
    private UserDetails mapFromUserToUserDetail(User usuario)
    {
    	String encodedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());
      	return org.springframework.security.core.userdetails.User.builder()
    	.username(usuario.getUsername())
    	.password(encodedPassword)
    	.authorities(usuario.getRol())
    	.build();
    }
}