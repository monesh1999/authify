package com.monesh.authify.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.monesh.authify.entity.UserEntity;
import com.monesh.authify.repository.UserRepo;


@Service
public class AppUserDetailsService implements UserDetailsService {
	
	
	private final UserRepo userRepository;
	public AppUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity existingUser = userRepository.findByEmail(email)
		.orElseThrow(() -> new UsernameNotFoundException("Email not found for the email "+email) );
		
		return new User(existingUser.getEmail(),existingUser.getPassword(),new ArrayList<>());
	}

}
