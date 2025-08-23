package com.monesh.authify.io;

public class AuthResponse {

	
	private String email;
	private String token;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	
	/**
	 * @param email
	 * @param token
	 */
	public AuthResponse(String email, String token) {
		this.email = email;
		this.token = token;
	}
	
}
