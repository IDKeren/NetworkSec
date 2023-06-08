package net;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.boundaries.Customer;
import net.boundaries.User;
import net.data.CustomerEntity;
import net.data.UserEntity;
import net.logic.BadRequestEx;
import net.logic.Hasher;
import net.logic.PasswordManager;

@Component
public class Converter {
	private ObjectMapper jackson;
	private final PasswordManager passwordManager;
	
	
	public Converter(PasswordManager passwordManager) {
		this.jackson = new ObjectMapper();
		this.passwordManager = passwordManager;
	}

	public UserEntity toEntity(User boundary) throws NoSuchAlgorithmException, InvalidKeySpecException {
		UserEntity entity = new UserEntity();

		entity.setId(boundary.getId());

		if (boundary.getUserName() == null) {
			throw new BadRequestEx("User NAME cannot be BLANK");
		} else {
			entity.setUserName(boundary.getUserName());
		}

		if (!(passwordManager.isPasswordComplex(boundary.getPassword()))) {
			throw new BadRequestEx(
					"Password must contain 10 character /n Atleast 1 special characther !@#$%^&*()_-+=<>?"
							+ "/n atleast 1 uppercase,lowercase and number");
		} else {
			String password = Hasher.hashPassword(boundary.getPassword());
			if (!(Hasher.checkPassword(boundary.getPassword(), password))) {
				throw new BadRequestEx("Password is not hashed");
			}
			if (boundary.getPassHistory() == null) {
				boundary.setPassHistory(entity.getPassHistory());
			}
			entity.setPassword(password);

		}

		if (boundary.getEmail() == null || boundary.getEmail().equals("")) {
			throw new BadRequestEx("Email is not VALID");
		} else {
			entity.setEmail(boundary.getEmail());
		}
		return entity;
	}

	public CustomerEntity toEntity(Customer boundary) {
		CustomerEntity entity = new CustomerEntity();

		entity.setLineCode(boundary.getLineCode());

		if (boundary.getCustomerName() == null) {
			throw new BadRequestEx("Customer NAME cannot be BLANK");
		} else {
			entity.setCustomerName(boundary.getCustomerName());
		}
		if (boundary.getService() == null) {
			entity.setService(boundary.getService());
		}

		return entity;
	}

	public String toEntity(Map<String, Object> data) {
		try {
			return this.jackson.writeValueAsString(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User toBoundary(UserEntity entity) {
		User boundary = new User();

		boundary.setId(entity.getId());
		boundary.setUserName(entity.getUserName());
		boundary.setPassword(entity.getPassword());
		boundary.setEmail(entity.getEmail());

		return boundary;
	}

	public Customer toBoundary(CustomerEntity entity) {
		Customer boundary = new Customer();

		boundary.setLineCode(entity.getLineCode());
		boundary.setCustomerName(entity.getCustomerName());
		boundary.setService(entity.getService());

		return boundary;
	}
}
