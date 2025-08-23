package com.monesh.authify.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
	
	

	 private final String SECRET = "GreenLeaf-create-by-monesh-ThisSecretKeyMustBeAtLeast32Bytes"; 
	 private final long EXPIRATION = 1000 * 60 * 2;
	 private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	 

	 public String generateToken(UserDetails userDetails) {
		 Map<String,Object> cliams = new HashMap<>();
		 return createToken(cliams,userDetails.getUsername());
		 
	 }

	 private String createToken(Map<String, Object> cliams, String username) {
		return Jwts.builder()
         .setClaims(cliams)
         .setIssuedAt(new Date(System.currentTimeMillis()))
         .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
         .signWith(secretKey, SignatureAlgorithm.HS256)
         .compact();
	 }
}
