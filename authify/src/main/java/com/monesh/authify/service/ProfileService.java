package com.monesh.authify.service;

import com.monesh.authify.io.ProfileRequest;
import com.monesh.authify.io.ProfileResponse;

public interface ProfileService {
    
    ProfileResponse createProfile(ProfileRequest request);

}
