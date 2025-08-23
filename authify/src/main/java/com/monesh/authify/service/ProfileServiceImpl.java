package com.monesh.authify.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.monesh.authify.entity.UserEntity;
import com.monesh.authify.io.ProfileRequest;
import com.monesh.authify.io.ProfileResponse;
import com.monesh.authify.repository.UserRepo;

import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection (Spring will automatically autowire this)
    public ProfileServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        UserEntity newProfile = convertToUserEntity(request);
        if(!userRepository.existsByEmail(request.getEmail())) {
        	newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");
        
    }

//    private UserEntity convertToUserEntity(ProfileRequest request) {
//        UserEntity entity = new UserEntity();
//        entity.setUserId(UUID.randomUUID().toString());
//        entity.setName(request.getName());
//        entity.setEmail(request.getEmail());
//        entity.setPassword(request.getPassword()); // plain for now
//        entity.setIsAccountVerified(false);
//        return entity;
//    }
    
    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // plain for now
                .isAccountVerified(false)
                .verifyOtp(null)                  // default null
                .verifyOtpExpireAt(0L) // e.g., 10 min expiry
                .resetOtp(null)                   // default null
                .resetOtpExpiredAt(null)          // default null
                .build();
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
