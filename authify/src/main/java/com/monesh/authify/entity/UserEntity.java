package com.monesh.authify.entity;

import java.sql.Timestamp;

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

    @Column(unique = true)
    private String userId;

    private String name;

    @Column(unique = true)
    private String email;
   

    private String password;
    private String verifyOtp;
    private Boolean isAccountVerified;
    private Long verifyOtpExpireAt;
    private String resetOtp;
    private Long resetOtpExpiredAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    // ----------- Constructors -----------

    public UserEntity() {
    }

    public UserEntity(Long id, String userId, String name, String email, String password,
                      String verifyOtp, Boolean isAccountVerified, Long verifyOtpExpireAt,
                      String resetOtp, Long resetOtpExpiredAt, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.verifyOtp = verifyOtp;
        this.isAccountVerified = isAccountVerified;
        this.verifyOtpExpireAt = verifyOtpExpireAt;
        this.resetOtp = resetOtp;
        this.resetOtpExpiredAt = resetOtpExpiredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ----------- Getters & Setters -----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyOtp() {
        return verifyOtp;
    }

    public void setVerifyOtp(String verifyOtp) {
        this.verifyOtp = verifyOtp;
    }

    public Boolean getIsAccountVerified() {
        return isAccountVerified;
    }

    public void setIsAccountVerified(Boolean isAccountVerified) {
        this.isAccountVerified = isAccountVerified;
    }

    public Long getVerifyOtpExpireAt() {
        return verifyOtpExpireAt;
    }

    public void setVerifyOtpExpireAt(Long verifyOtpExpireAt) {
        this.verifyOtpExpireAt = verifyOtpExpireAt;
    }

    public String getResetOtp() {
        return resetOtp;
    }

    public void setResetOtp(String resetOtp) {
        this.resetOtp = resetOtp;
    }

    public Long getResetOtpExpiredAt() {
        return resetOtpExpiredAt;
    }

    public void setResetOtpExpiredAt(Long resetOtpExpiredAt) {
        this.resetOtpExpiredAt = resetOtpExpiredAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
