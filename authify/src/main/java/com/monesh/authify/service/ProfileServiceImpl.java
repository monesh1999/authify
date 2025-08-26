package com.monesh.authify.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.monesh.authify.entity.UserEntity;
import com.monesh.authify.io.ProfileRequest;
import com.monesh.authify.io.ProfileResponse;
import com.monesh.authify.repository.UserRepo;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    // Constructor injection (Spring will automatically autowire this)
    public ProfileServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
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

    private UserEntity convertToUserEntity(ProfileRequest request) {
        UserEntity entity = new UserEntity();
        entity.setUserId(UUID.randomUUID().toString());
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPassword(passwordEncoder.encode(request.getPassword())); // plain for now
        entity.setIsAccountVerified(false);
        entity.setVerifyOtp(null);
        entity.setVerifyOtpExpireAt(0L);
        entity.setResetOtp(null);
        entity.setResetOtpExpiredAt(null);
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

	@Override
	public ProfileResponse getProfile(String email) {
		UserEntity existingUser = userRepository.findByEmail(email)
		.orElseThrow(() -> new UsernameNotFoundException("user not found :"+email));
		return convertToProfileResponse(existingUser);
	}

	@Override
	public void sendResetOtp(String email) {
		UserEntity existingEntity = userRepository.findByEmail(email)
		.orElseThrow(() -> new UsernameNotFoundException("User not found : "+email));
		//genrate otp 6 digit
		String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));  
		//calclate expried time 
		long expiryTime =System.currentTimeMillis()+(15 * 60 * 1000 ); //(15mins)
		
		//update the profile/user
		existingEntity.setResetOtp(otp);
		existingEntity.setResetOtpExpiredAt(expiryTime);
		//save the database
		userRepository.save(existingEntity);
		try {
			emailService.sentResetOtpEmail(existingEntity.getEmail(), otp);
		}catch(Exception ex) {
			throw new RuntimeException("unable to send email");
			
		}
		
		
	}

	@Override
	public void resetPassword(String email, String otp, String newPassword) {
		UserEntity existinguser = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found : "+email));
		if(existinguser.getResetOtp()==null || !existinguser.getResetOtp().equals(otp)) {
			throw new RuntimeException("Invaild OTP");
		}
		if(existinguser.getResetOtpExpiredAt() < System.currentTimeMillis()) {
			throw new RuntimeException("OTP Expired");
		}
		
		existinguser.setPassword(passwordEncoder.encode(newPassword));
		existinguser.setResetOtp(null);
		existinguser.setResetOtpExpiredAt(0L);
		userRepository.save(existinguser);
		
		
	}

	@Override
	public void sentOtp(String email) {
		UserEntity existinguser = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found : "+email));
		if(existinguser.getIsAccountVerified() != null && existinguser.getIsAccountVerified()) {
			return;
		}
		
		//genrate otp 6 digit
				String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));  
				//calclate expried time 
				long expiryTime =System.currentTimeMillis()+(24 * 60 * 60 * 1000 ); // 24 hours
				
				//update the user entity
				
				existinguser.setVerifyOtp(otp);
				existinguser.setVerifyOtpExpireAt(expiryTime);
				
				// save to DB
				
				userRepository.save(existinguser);
				
				try {
					emailService.sendOtpEmail(existinguser.getEmail(), otp);
				}catch(Exception ex) {
					throw new RuntimeException("unable to send email");
				}
		
	}

	@Override
	public void veriftOtp(String email, String otp) {
		UserEntity existinguser = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found : "+email));
		if(existinguser.getVerifyOtp() == null || !existinguser.getVerifyOtp().equals(otp)) {
			throw new RuntimeException("Invalid OTP");
		}
		if(existinguser.getVerifyOtpExpireAt() < System.currentTimeMillis()) {
			throw new RuntimeException("OTP Expired");
		}
		existinguser.setIsAccountVerified(true);
		existinguser.setVerifyOtp(null);
		existinguser.setVerifyOtpExpireAt(0L);
		userRepository.save(existinguser);
		
	}

//	@Override
//	public String getLoggedInUserId(String email) {
//		UserEntity existinguser = userRepository.findByEmail(email)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found : "+email));
//		return existinguser.getUserId();
//		
//	}
}
