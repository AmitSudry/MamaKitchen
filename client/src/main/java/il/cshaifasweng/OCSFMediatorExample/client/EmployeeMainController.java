package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmployeeMainController implements Initializable 
{
	private static Employee employee;
	
	@FXML
	private Label EmployeeGreeting;
	 
	@FXML
	private TextField ActionStatus; //check for employee permissions
	
	@FXML
	void SwitchToBranchOccupationController(ActionEvent event) throws IOException 
	{
		if(employee==null)
			return;
				
		App.setRoot("viewOccupation");
	}
	
	
	@FXML
	void SwitchToUpdateItemController(ActionEvent event) throws IOException 
	{
		if(employee==null)
			return;
		
		if(employee.getType()!=2) //if you're not the dietitian
		{
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		
		App.setRoot("updateItem");
	}
	
	@FXML
	void SwitchToUpdateRegulationsController(ActionEvent event) 
	{
		if(employee==null)
			return;
		
		if(employee.getType()!=3) //if you're not a customer service employee
		{
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		
		//App.setRoot("updateRegulations");
	}
	
	@FXML
	void SwitchToViewComplaintsController(ActionEvent event) throws IOException 
	{
		if(employee==null)
			return;
		
		if(employee.getType()!=3) //if you're not a customer service employee
		{
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		
		App.setRoot("viewComplaints");
	}
	
	@FXML
	void SwitchToViewReportsController(ActionEvent event) 
	{
		if(employee==null)
			return;
		
		if(employee.getType()!=5) //not the chain manager
		{
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		
		//App.setRoot("viewReports");
	}
	
    @Subscribe
    public void onEmployeeEvent(Employee event) //receives this event at one with "LoginController"
    {
    	this.employee = event;
    	EmployeeGreeting.setText("Hello " + employee.getUserName());
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
	}
}