package com.monesh.authify.io;

import com.monesh.authify.entity.UserEntity.Builder;

public class ProfileResponse {

    private String userId;
    private String name;
    private String email;
    private Boolean isAccountVerified;

    // Default constructor
    public ProfileResponse() {
    }

    // All-args constructor
    public ProfileResponse(String userId, String name, String email, Boolean isAccountVerified) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.isAccountVerified = isAccountVerified;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsAccountVerified() {
        return isAccountVerified;
    }

    public void setIsAccountVerified(Boolean isAccountVerified) {
        this.isAccountVerified = isAccountVerified;
    }

    // toString() (optional, useful for debugging/logging)
    @Override
    public String toString() {
        return "ProfileResponse{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isAccountVerified=" + isAccountVerified +
                '}';
    }

	public static Builder builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
