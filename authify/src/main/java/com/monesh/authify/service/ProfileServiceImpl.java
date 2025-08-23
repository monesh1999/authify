package com.monesh.authify.service;

import org.springframework.stereotype.Service;

import com.monesh.authify.entity.UserEntity;
import com.monesh.authify.io.ProfileRequest;
import com.monesh.authify.io.ProfileResponse;
import com.monesh.authify.repository.UserRepo;

import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepo userRepository;

    // Constructor injection (Spring will automatically autowire this)
    public ProfileServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        UserEntity newProfile = convertToUserEntity(request);
        newProfile = userRepository.save(newProfile);
        return convertToProfileResponse(newProfile);
    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        UserEntity entity = new UserEntity();
        entity.setUserId(UUID.randomUUID().toString());
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword()); // plain for now
        entity.setIsAccountVerified(false);
        return entity;
    }

    private ProfileResponse convertToProfileResponse(UserEntity entity) {
        ProfileResponse response = new ProfileResponse();
        response.setUserId(entity.getUserId());
        response.setName(entity.getName());
        response.setEmail(entity.getEmail());
        response.setIsAccountVerified(entity.getIsAccountVerified());
        return response;
    }
}
