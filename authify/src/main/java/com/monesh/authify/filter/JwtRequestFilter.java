package com.monesh.authify.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.monesh.authify.service.AppUserDetailsService;
import com.monesh.authify.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final AppUserDetailsService appUserDetailsServices;
	private final JwtUtil jwtUtil;
	
	public JwtRequestFilter(AppUserDetailsService appUserDetailsServices, JwtUtil jwtUtil) {
	    this.appUserDetailsServices = appUserDetailsServices;
	    this.jwtUtil = jwtUtil;
	}
	
	public static final List<String> PUBLIC_URLS = List.of("/login", "/register", "/send-reset-otp", "/reset-password", "/logout");


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();
		
		if(PUBLIC_URLS.contains(path)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwt =null;
		String email = null;
		
		final String authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
		}
		//2. if not found header , check cookies
		if (jwt == null) {
		    Cookie[] cookies = request.getCookies();  // plural for clarity
		    if (cookies != null) {
		        for (Cookie c : cookies) {
		            if ("jwt".equals(c.getName())) {
		                jwt = c.getValue();
		                break;
		            }
		        }
		    }
		}
		//3. validate the token and set security context
		if(jwt != null) {
			email = jwtUtil.extractEmail(jwt);
			if(email != null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = appUserDetailsServices.loadUserByUsername(email);
				if(jwtUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken auth =
	                        new UsernamePasswordAuthenticationToken(
	                                userDetails, null, userDetails.getAuthorities());

	                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	                SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}

		
		filterChain.doFilter(request, response);
		
	}

}
