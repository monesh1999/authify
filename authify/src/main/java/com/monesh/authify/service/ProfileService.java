package com.monesh.authify.service;

import com.monesh.authify.io.ProfileRequest;
import com.monesh.authify.io.ProfileResponse;

public interface ProfileService {
    
    ProfileResponse createProfile(ProfileRequest request);
    
    ProfileResponse getProfile(String email);
    
    void sendResetOtp(String email);
    
    void resetPassword(String email,String otp,String newPassword);
    
    void sentOtp(String email);
    
    void veriftOtp(String email,String otp);
    
//    String getLoggedInUserId(String email);
    
    

}
