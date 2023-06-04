package net.boundaries;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class User {
	private String id;
	private String userName;
	private String password;
	private String email;
	private List<String> passHistory;
	

	public User() {
	}

	public User(String id, String userName, String password, String email) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.passHistory = new ArrayList<>();
	}

	public List<String> getPassHistory() {
		return passHistory;
	}

	public void setPassHistory(List<String> passHistory) {
		this.passHistory = passHistory;
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
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", passHistory=" + passHistory + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(isValid(email)) {
		this.email = email;
		}else {
			this.email = "";
		}
	}
	
	public boolean isValid(String email) {
        String emailRegex = "([a-zA-Z0-9_+&.-]+)@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


}
