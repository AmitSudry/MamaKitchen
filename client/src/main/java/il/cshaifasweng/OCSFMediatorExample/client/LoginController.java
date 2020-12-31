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
import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.Login;

public class LoginController implements Initializable 
{
	private boolean forwardedEmployee = false;
	
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
    void login(ActionEvent event) throws IOException 
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
    	
    	//App.setRoot("employeeMain");
    }
    
    @Subscribe
    public void onEmployeeEvent(Employee event) throws IOException 
    {
    	if(!forwardedEmployee) 
    	{
    		forwardedEmployee = true;
    		
    		if(event.getType()==-1)
        	{
        		loginStatus.setText("Incorrect username and/or password!");
        	}
        	else
        	{
        	   	App.setRoot("employeeMain");
        	   	//then forward the employee to the "EmployeeMainController"
        	   	Login l = new Login(event.getUserName(), event.getPassword());
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
    	}
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
	}
}