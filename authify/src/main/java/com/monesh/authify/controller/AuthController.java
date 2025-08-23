package com.monesh.authify.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monesh.authify.io.AuthRequest;
import com.monesh.authify.io.AuthResponse;
import com.monesh.authify.service.AppUserDetailsService;
import com.monesh.authify.util.JwtUtil;

@RestController
public class AuthController {
	
	
	private final AppUserDetailsService appUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    
    public AuthController(AppUserDetailsService appUserDetailsService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.appUserDetailsService = appUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
	
	public ResponseEntity<?> login(@RequestBody AuthRequest request){
		
		try {
			authenticate(request.getEmail(),request.getPassword());
			final UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getEmail());
			final String jwtToken = jwtUtil.generateToken(userDetails);
			ResponseCookie cookie = ResponseCookie.from("jwt",jwtToken)
					.httpOnly(true)
					.path("/")
					.maxAge(Duration.ofDays(1))
					.sameSite("Strict")
					.build();
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString())
					.body(new AuthResponse(request.getEmail(),jwtToken));
				
			}catch(BadCredentialsException ex) {
				Map<String , Object> error = new HashMap<>();
				error.put("error", true);
				error.put("message","Email or Password is incorrect");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
				
			}catch(DisabledException ex) {
				Map<String , Object> error = new HashMap<>();
				error.put("error", true);
				error.put("message","Account is Disabled");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
			}catch(Exception ex) {
				Map<String , Object> error = new HashMap<>();
				error.put("error", true);
				error.put("message","Authentication is failed");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
				
			}
		}
	

	private void authenticate(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		
	}

}
