package net.logic;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import net.data.UserEntity;

@Component
public class PasswordManager {

	private static final int MIN_UPPERCASE = 1;
	private static final int MIN_LOWERCASE = 1;
	private static final int MIN_DIGITS = 1;
	private static final int MIN_SPECIAL_CHARACTERS = 1;
	private static List<String> passwordHistory;
	private static int loginAttempts;
	private final ConfigReader configReader;
	
	@Autowired
	public PasswordManager(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public void LoginManager() {
		loginAttempts = 0;
	}

	public void LoginAttempts() {
		Config config = configReader.getConfig();
		int maxLoginAtmp = config.getMaxLoginAtempt();
		if (loginAttempts >= maxLoginAtmp) {
			throw new RuntimeException("Maximum login attempts reached "); // Maximum login attempts reached
		} else
			loginAttempts++;
	}

	public static boolean isPasswordValid(UserEntity user, String userPassowrd) {

		passwordHistory = user.getPassHistory();

		// Check if the user's password is in the correct password
		if (passwordHistory.get(0).contentEquals(userPassowrd)) {
			loginAttempts = 0;
			return true;
		} else

			return false;
	}

	public boolean isNewPasswordValid(UserEntity user, String newPassword) {
		Config config = configReader.getConfig();
		int maxPasswordHis = config.getMaxPasswordHistory();
		passwordHistory = user.getPassHistory();

		// Check if the new password is in the password history
		if (passwordHistory.contains(newPassword)) {
			return false;
		}

		// Limit the password history to the last three passwords
		if (passwordHistory.size() >= maxPasswordHis) {
			passwordHistory = passwordHistory.subList(passwordHistory.size() - maxPasswordHis, passwordHistory.size());
		}

		// Add the new password to the password history
		passwordHistory.add(newPassword);

		// Update the user entity's password history
		user.setPassHistory(passwordHistory);

		return true;
	}

	private String sendCodeToEmail() {
		int codeLength = 7;
		String randomCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, codeLength);
		String hashedCode = null;
		try {
			MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
			byte[] codeBytes = randomCode.getBytes(StandardCharsets.UTF_8);
			byte[] hashedBytes = sha1Digest.digest(codeBytes);
			hashedCode = bytesToHexString(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error hashing password.", e);
		}
		return hashedCode;
	}

	public boolean isDictionaryWord(String password, Set<String> dictionary) {
		String[] words = password.split("\\s+"); // Split the password into individual words

		for (String word : words) {
			if (dictionary.contains(word.toLowerCase())) {
				return true; // Password contains a dictionary word
			}
		}

		return false; // Password does not contain any dictionary words
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public boolean isPasswordComplex(String password) {
		Config config = configReader.getConfig();
		int length = config.getPasswordLength();
		String specialCharacters = config.getSpecialCharacters();
		if (password.length() != length) {
			return false; // Password is not in length
		}

		int uppercaseCount = 0;
		int lowercaseCount = 0;
		int digitCount = 0;
		int specialCharacterCount = 0;

		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				uppercaseCount++;
			} else if (Character.isLowerCase(c)) {
				lowercaseCount++;
			} else if (Character.isDigit(c)) {
				digitCount++;
			} else if (specialCharacters.contains(String.valueOf(c))) {
				specialCharacterCount++;
			}
		}

		return uppercaseCount >= MIN_UPPERCASE && lowercaseCount >= MIN_LOWERCASE && digitCount >= MIN_DIGITS
				&& specialCharacterCount >= MIN_SPECIAL_CHARACTERS;
	}

	public static String generateLineCodeId() {
		// Define the maximum length of the line of code ID
		int maxLength = 10;

		// Create a StringBuilder to store the generated ID
		StringBuilder lineCodeId = new StringBuilder();

		// Create an instance of Random
		Random random = new Random();

		// Generate random digits until the desired length is reached
		while (lineCodeId.length() < maxLength) {
			// Generate a random digit (0-9)
			int digit = random.nextInt(10);

			// Append the digit to the line of code ID
			lineCodeId.append(digit);
		}

		return lineCodeId.toString();
	}

}
