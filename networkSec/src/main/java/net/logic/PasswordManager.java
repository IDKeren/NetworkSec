package net.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;
import net.data.UserEntity;

public class PasswordManager {
	private static final int MAX_PASSWORD_HISTORY = 3;

	private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_-+=<>?";
	private static final int LENGTH = 10;
	private static final int MIN_UPPERCASE = 1;
	private static final int MIN_LOWERCASE = 1;
	private static final int MIN_DIGITS = 1;
	private static final int MIN_SPECIAL_CHARACTERS = 1;

	public static void isPasswordValid(String newPassword, ArrayList<String> passHistory)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		if (passHistory.size() >= MAX_PASSWORD_HISTORY) {
			passHistory.subList(passHistory.size() - MAX_PASSWORD_HISTORY, passHistory.size());
			// Remove oldest password from history
		}
		
		if (passHistory.contains(newPassword)) {
			throw new BadRequestEx(" Password has been used before");
			// Password has been used before
		}

		passHistory.add(newPassword); // Add new password to history

	}
	public static boolean isNewPasswordValid(UserEntity user, String newPassword) {
        
		List<String> passwordHistory = user.getPassHistory();

        // Check if the new password is in the password history
        if (passwordHistory.contains(newPassword)) {
            return false;
        }

        // Limit the password history to the last three passwords
        if (passwordHistory.size() >= MAX_PASSWORD_HISTORY) {
            passwordHistory = passwordHistory.subList(passwordHistory.size() - MAX_PASSWORD_HISTORY, passwordHistory.size());
        }

        // Add the new password to the password history
        passwordHistory.add(newPassword);

        // Update the user entity's password history
        user.setPassHistory(passwordHistory);

        return true;
    }

	private boolean verifyPassword(String password, String hashedPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(password.getBytes());
			String hashedPasswordAttempt = bytesToHex(hashedBytes);
			return hashedPassword.equals(hashedPasswordAttempt);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error hashing password.", e);
		}
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

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static boolean isPasswordComplex(String password) {
		if (password.length() != LENGTH) {
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
			} else if (SPECIAL_CHARACTERS.contains(String.valueOf(c))) {
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
