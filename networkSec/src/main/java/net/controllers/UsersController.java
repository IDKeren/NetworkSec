package net.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import net.boundaries.User;
import net.logic.UserService;

@RestController
@CrossOrigin
public class UsersController {
	private UserService users;

	@Autowired
	public UsersController(UserService users) {
		this.users = users;
	}

	@RequestMapping(
			path = { "/users" },
			method = { RequestMethod.POST },
			produces = {MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE }
			)
	public User createUser(@RequestBody User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return this.users.addToDb(user);
	}

	@RequestMapping(path = { "/users/update/{userName}" }, method = { RequestMethod.PUT }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public void updateSpecificMessage(@RequestBody User update) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.users.updateUser(update);
	}

	@RequestMapping(path = "/users/deleteByID/{usersId}", method = { RequestMethod.DELETE })
	public void deleteUserById(@PathVariable("userId") String userId) {
		this.users.deleteUserById(userId);
	}

	@RequestMapping(path = "/users", method = { RequestMethod.DELETE })
	public void deleteAllUsers() {
		this.users.deleteAllUsers();
	}

	@RequestMapping(path = { "/users" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public User[] hello() {
		List<User> rv = this.users.getAllUsers();
		return rv.toArray(new User[0]);
	}

	@RequestMapping(path = { "/users/byID/{id}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public User getById(@PathVariable("id") String id) {
		return this.users.getUserById(id);
	}
	
	@RequestMapping(path = { "/users/login/{userName},{password}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public User userLogin(@PathVariable("userName") String userName,@PathVariable("password") String password) {
		return this.users.login(userName,password);
	}
}
