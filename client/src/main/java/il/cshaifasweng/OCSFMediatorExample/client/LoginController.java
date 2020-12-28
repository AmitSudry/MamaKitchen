package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import il.cshaifasweng.OCSFMediatorExample.entities.Login;

public class LoginController implements Initializable 
{
	@FXML
	private TextField loginStatus;
	 
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField userName;
	
    @FXML
    private void switchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
    
   
    @FXML
    void login(ActionEvent event) 
    {
    	if(userName.getText().equals("") || password.getText().equals(""))
    	{
    		loginStatus.setText("You're missing fields!");
    		return;
    	}
    	
    	Login l = new Login(userName.getText(), password.getText());
    	userName.setText("");
    	password.setText("");
    	try 
    	{
        	SimpleClient.getClient().sendToServer(l);
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Subscribe
    public void onLoginEvent(LoginEvent event) 
    {
    
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
	}
}