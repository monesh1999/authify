package com.monesh.authify.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String verifyOtp;
    private Boolean isAccountVerified = false;
    private Long verifyOtpExpireAt;
    private String resetOtp;
    private Long resetOtpExpiredAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    // ----------- Private constructor for Builder -----------
    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.verifyOtp = builder.verifyOtp;
        this.isAccountVerified = builder.isAccountVerified;
        this.verifyOtpExpireAt = builder.verifyOtpExpireAt;
        this.resetOtp = builder.resetOtp;
        this.resetOtpExpiredAt = builder.resetOtpExpiredAt;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    // ----------- Getters -----------
    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getVerifyOtp() { return verifyOtp; }
    public Boolean getIsAccountVerified() { return isAccountVerified; }
    public Long getVerifyOtpExpireAt() { return verifyOtpExpireAt; }
    public String getResetOtp() { return resetOtp; }
    public Long getResetOtpExpiredAt() { return resetOtpExpiredAt; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }

    // ----------- Builder class -----------
    public static class Builder {
        private Long id;
        private String userId = UUID.randomUUID().toString(); // default UUID
        private String name;
        private String email;
        private String password;
        private String verifyOtp;
        private Boolean isAccountVerified = false;
        private Long verifyOtpExpireAt;
        private String resetOtp;
        private Long resetOtpExpiredAt;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder verifyOtp(String verifyOtp) { this.verifyOtp = verifyOtp; return this; }
        public Builder isAccountVerified(Boolean isAccountVerified) { this.isAccountVerified = isAccountVerified; return this; }
        public Builder verifyOtpExpireAt(Long verifyOtpExpireAt) { this.verifyOtpExpireAt = verifyOtpExpireAt; return this; }
        public Builder resetOtp(String resetOtp) { this.resetOtp = resetOtp; return this; }
        public Builder resetOtpExpiredAt(Long resetOtpExpiredAt) { this.resetOtpExpiredAt = resetOtpExpiredAt; return this; }
        public Builder createdAt(Timestamp createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; return this; }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }

    // ----------- Static method to start builder -----------
    public static Builder builder() {
        return new Builder();
    }
}
