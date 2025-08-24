package com.monesh.authify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.monesh.authify.io.ProfileRequest;
import com.monesh.authify.io.ProfileResponse;
import com.monesh.authify.service.ProfileService;

import jakarta.validation.Valid;

@RestController
public class ProfileController {
    
    private final ProfileService profileService;
    
    
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.createProfile(request); 
        return response;
    }
    
    @GetMapping("/test")
    public String test() {
    	return "Auth is working";
    }
    
    
    @GetMapping("/profile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name")String email) {
    	return profileService.getProfile(email);
    }
}
