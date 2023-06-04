package net.data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String userName;
	private String password;
	private String email;
	private List<String> passHistory;

	public List<String> getPassHistory() {
		return passHistory;
	}

	public void setPassHistory(List<String> passHistory) {
		this.passHistory = passHistory;
	}

	public UserEntity() {
		this.passHistory = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", passHistory=" + passHistory + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
