package com.heroes.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping(value = "login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)
    {
        try {
			return ResponseEntity.ok(authService.login(request));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
    }

    @PostMapping(value = "register")
    public ResponseEntity<Integer> register(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}
