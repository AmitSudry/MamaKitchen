package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;

public class Login implements Serializable 
{
	private static final long serialVersionUID = -8224097662914849956L;
	
	private String username;
	private String password; 
		
	public Login()
	{
		super();
	}

	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + password + "]";
	}

}
