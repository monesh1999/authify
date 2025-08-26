package com.monesh.authify.io;

import jakarta.validation.constraints.NotBlank;

public class ResetPasswordRequest {
	
	
	@NotBlank(message = "new password is required")
	private String newpassword;
	/**
	 * @param newpassword
	 * @param otp
	 * @param email
	 */
	public ResetPasswordRequest(@NotBlank(message = "new password is required") String newpassword,
			@NotBlank(message = "new otp is required") String otp,
			@NotBlank(message = "new email is required") String email) {
		this.newpassword = newpassword;
		this.otp = otp;
		this.email = email;
	}
	/**
	 * @return the newpassword
	 */
	public String getNewpassword() {
		return newpassword;
	}
	/**
	 * @param newpassword the newpassword to set
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}
	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	@NotBlank(message = "new otp is required")
	private String otp;
	@NotBlank(message = "new email is required")
	private String email;

}
