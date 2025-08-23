package com.monesh.authify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.monesh.authify.io.ProfileRequest;
import com.monesh.authify.io.ProfileResponse;
import com.monesh.authify.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProfileController {
    
    private final ProfileService profileService;
    
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.createProfile(request); 
        return response;
    }
}
