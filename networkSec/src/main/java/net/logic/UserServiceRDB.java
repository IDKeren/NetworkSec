package net.logic;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.Converter;
import net.boundaries.User;
import net.dal.Crud;
import net.data.UserEntity;
import jakarta.annotation.PostConstruct;

@Service
public class UserServiceRDB implements UserService {
	private Crud userCrud;
	private Converter converter;
	private String nameFromSpringConfig;
	private final PasswordManager passwordManager;
	
	@Autowired
	public UserServiceRDB(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }
	
	@Autowired
	public void setUserCrud(Crud userCrud) {
		this.userCrud = userCrud;
	}

	@Autowired
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	// have spring inject a configuration to this method
	@Value("${spring.application.name:defaultAfekaDemoValue}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@PostConstruct
	public void init() {
		System.err.println("**** spring.application.name=" + this.nameFromSpringConfig);
	}

	@Override
	@Transactional
	public User addToDb(User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
		// TODO have the database define the id, instead of the server or find another
		// mechanisms to generate the object
//			user.setId(System.currentTimeMillis());
		String userId = UUID.randomUUID().toString();
		user.setId(userId);

		UserEntity entity = this.converter.toEntity(user);

		List<UserEntity> userEntities = this.userCrud.findAll();
		for (UserEntity u : userEntities) {
			String name = u.getUserName();
			String email = u.getEmail();
			{
				if (name.equals(user.getUserName()))
					throw new BadRequestEx("Duplicate user name found: " + name + " Please try again");
			}
			if (email.equals(user.getEmail())) {
				throw new BadRequestEx("This Email is already taken: " + email + " Please try again");
			}
		}

		entity = this.userCrud.save(entity);
		User rv = this.converter.toBoundary(entity);
		return rv;

	}

	@Override
	@Transactional
	public void updateUser(User update) throws NoSuchAlgorithmException, InvalidKeySpecException {
		UserEntity existing = this.userCrud.findByUserName(update.getUserName()).orElseThrow(() -> new RuntimeException(
				"could not update user with name: " + update.getUserName() + " since it does not exist"));

		// update entity

		if (update.getUserName() != null) {
			existing.setUserName(update.getUserName());
		}

		if (update.getPassword() == null || !(passwordManager.isNewPasswordValid(existing, update.getPassword()))) {
			throw new BadRequestEx("Password has been used before");
		} else {
			existing.setPassword(Hasher.hashPassword(update.getPassword()));
		}

		if (update.getEmail() != null) {
			existing.setEmail(update.getEmail());
		}

		// save (UPDATE) entity to database if message was indeed updated
		this.userCrud.save(existing);
	}

	@Override
	@Transactional
	public void deleteUserById(String userId) {
		this.userCrud.deleteById(userId);
	}

	@Override
	@Transactional
	public void deleteAllUsers() {
		this.userCrud.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		List<UserEntity> entities = this.userCrud.findAll();
		List<User> rv = new ArrayList<>();
		for (UserEntity e : entities) {
			rv.add(this.converter.toBoundary(e));
		}

		return rv;
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserById(String id) {
		Optional<UserEntity> op = this.userCrud.findById(id);

		if (op.isPresent()) {
			UserEntity entity = op.get();
			return this.converter.toBoundary(entity);
		} else {
			throw new RuntimeException("Could not find message by id: " + id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User login(String userName, String password) {
		Optional<UserEntity> op = this.userCrud.findByUserName(userName);

		if (op.isPresent()) {
			UserEntity entity = op.get();
			if (PasswordManager.isPasswordValid(entity, password)) {
				return this.converter.toBoundary(entity);
			} else {
				passwordManager.LoginAttempts();
				throw new RuntimeException("Could not find message by Password: " + password);
			}
		} else {
			passwordManager.LoginAttempts();
			throw new RuntimeException("Could not find message by User Name: " + userName);

		}
	}

}
