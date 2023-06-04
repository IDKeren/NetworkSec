package net.logic;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import net.boundaries.User;


public interface UserService {

	public User addToDb(User user) throws NoSuchAlgorithmException, InvalidKeySpecException;

	public void updateUser(User update) throws NoSuchAlgorithmException, InvalidKeySpecException;

	public void deleteUserById(String userId);

	public void deleteAllUsers();

	public List<User> getAllUsers();

	public User getUserById(String id);

	public User getUserByName(String userName);
	
	 
}


