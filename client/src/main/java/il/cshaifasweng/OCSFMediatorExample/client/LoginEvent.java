package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Login;

public class LoginEvent 
{
	private Login loginDetails;
	
	public Login getLoginDetails() 
	{
		return loginDetails;
	}

	public LoginEvent(Login loginInfo) 
	{
		this.loginDetails = loginInfo;
	}
}
