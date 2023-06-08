package net.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
	 @JsonProperty("MAX_PASSWORD_HISTORY")
	    private int maxPasswordHistory;
	 @JsonProperty("MAX_LOGIN_ATTEMPT")
    private int maxLoginAtempt;
	 @JsonProperty("SPECIAL_CHARACTERS")
    private String specialCharacters;
	 @JsonProperty("PASSWORD_LENGTH")
    private int passwordLength ;

	public Config() {
		// Empty constructor
	}

	public int getMaxPasswordHistory() {
		return maxPasswordHistory;
	}

	public void setMaxPasswordHistory(int maxPasswordHistory) {
		this.maxPasswordHistory = maxPasswordHistory;
	}

	public int getMaxLoginAtempt() {
		return maxLoginAtempt;
	}

	public void setMaxLoginAtempt(int maxLoginAtempt) {
		this.maxLoginAtempt = maxLoginAtempt;
	}

	public String getSpecialCharacters() {
		return specialCharacters;
	}

	public void setSpecialCharacters(String specialCharacters) {
		this.specialCharacters = specialCharacters;
	}

	public int getPasswordLength() {
		return passwordLength;
	}

	public void setPasswordLength(int passwordLength) {
		this.passwordLength = passwordLength;
	}

	

	
}
