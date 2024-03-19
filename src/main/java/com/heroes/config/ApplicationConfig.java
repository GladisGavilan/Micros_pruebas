package com.heroes.config;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.heroes.auth.user.User;
import com.heroes.auth.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;
    
    @Bean
    public InMemoryUserDetailsManager userDetails() throws Exception{
    	java.util.List<UserDetails> users = userRepository.findAll()
    			.stream()
    			.map(usuario -> mapFromUserToUserDetail(usuario))
    			.collect(Collectors.toList());
    	return new InMemoryUserDetailsManager(users);
    }
    
	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
	
		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);
	
		return providerManager;
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