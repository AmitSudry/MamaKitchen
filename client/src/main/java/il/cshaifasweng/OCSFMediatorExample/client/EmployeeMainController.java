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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmployeeMainController implements Initializable 
{
	
	@FXML
	private TextField ActionStatus; //check for employee permissions
	
	@FXML
	void SwitchToLoginController() throws IOException
	{
		App.setRoot("login");
	}
   
	
	@FXML
	void SwitchToBranchOccupationController(ActionEvent event) 
	{
	
	}
	
	
	@FXML
	void SwitchToUpdateItemController(ActionEvent event) 
	{
	
	}
	
	@FXML
	void SwitchToUpdateRegulationsController(ActionEvent event) 
	{
	
	}
	
	@FXML
	void SwitchToViewComplaintsController(ActionEvent event) 
	{
	
	}
	
	@FXML
	void SwitchToViewReportsController(ActionEvent event) 
	{
	
	}
	
    @Subscribe
    public void onWarningEvent(GetItemsEvent event) 
    {
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		
		
		try //Gather the menu info from the server 
    	{
			SimpleClient.getClient().sendToServer("#showMenu");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}