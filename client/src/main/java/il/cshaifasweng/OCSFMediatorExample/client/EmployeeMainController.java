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
import javafx.scene.control.Button;

public class EmployeeMainController implements Initializable 
{
	private static Employee employee;
	
	@FXML
	private Label EmployeeGreeting;
	 
	@FXML
	private TextField ActionStatus; //check for employee permissions
	
	@FXML
	private Label priceChangesText;
	
	@FXML
	private TextArea regulationsText;
	
	private static boolean isPriceChangesAllowed = true;
	
	private static int openHourHaifa, openMinuteHaifa, closeHourHaifa, closeMinuteHaifa;
	private static int openHourJerusalem, openMinuteJerusalem, closeHourJerusalem, closeMinuteJerusalem;
	private static int maxOpen, maxClose;
	private static boolean isDelivery;
	
	public void setReg(String regulations) {
		String[] tokens = regulations.split("\\s+");
		openHourHaifa = Integer.parseInt(tokens[1]);
		openMinuteHaifa = Integer.parseInt(tokens[2]);
		closeHourHaifa = Integer.parseInt(tokens[3]);
		closeMinuteHaifa = Integer.parseInt(tokens[4]);
		openHourJerusalem = Integer.parseInt(tokens[5]);
		openMinuteJerusalem = Integer.parseInt(tokens[6]);
		closeHourJerusalem = Integer.parseInt(tokens[7]);
		closeMinuteJerusalem = Integer.parseInt(tokens[8]);
		maxOpen = Integer.parseInt(tokens[9]);
		maxClose = Integer.parseInt(tokens[10]);
		isDelivery = Boolean.parseBoolean(tokens[11]);
		
		
		String msg = "Wellcome to Mom's kitchen!\r\n"
		+ "current regulations:\r\n"
		+ "Open and close hours:\r\n"
		+ "Haifa - ";
		if(openHourHaifa < 10)
			msg += "0";
		msg += String.valueOf(openHourHaifa) + ":"; 
		if(openMinuteHaifa < 10)
			msg += "0";
		msg += String.valueOf(openMinuteHaifa) + " - ";
		if(closeHourHaifa < 10)
			msg += "0";
		msg += String.valueOf(closeHourHaifa) + ":";
		if(closeMinuteHaifa < 10)
			msg += "0";
		msg += String.valueOf(closeMinuteHaifa) + "\r\n"
		+ "Jerusalem - ";
		if(openHourJerusalem < 10)
			msg += "0";
		msg += String.valueOf(openHourJerusalem) + ":"; 
		if(openMinuteJerusalem < 10)
			msg += "0";
		msg += String.valueOf(openMinuteJerusalem) + " - ";
		if(closeHourJerusalem < 10)
			msg += "0";
		msg += String.valueOf(closeHourJerusalem) + ":";
		if(closeMinuteJerusalem < 10)
			msg += "0";
		msg += String.valueOf(closeMinuteJerusalem) + "\r\n"
		+ "Maximum custumers in open space - " + String.valueOf(maxOpen) + " people\r\n"
		+ "maximum custumers in closed space - " + String.valueOf(maxClose) + " people\r\n";
		if(isDelivery)
			msg += "we do deliveries right now \r\n";
		else
			msg += "we don't do deliveries right now \r\n";
		msg += "tank you and have a nice day";
		
		
		regulationsText.setText(msg);
	}
	
	@FXML
	void SwitchToBranchOccupationController(ActionEvent event) throws IOException 
	{
		if(employee==null)
			return;
				
		App.setRoot("viewOccupation");
	}
	
	@FXML
	void priceButtonChanged(ActionEvent event) throws IOException {
		if(employee.getType() != 5) {
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		if(isPriceChangesAllowed) {
			priceChangesText.setText("Permit Price Changes");
			ActionStatus.setText("Price Changes Prevented!");
		} else {
			priceChangesText.setText("Prevent Price Changes");
			ActionStatus.setText("Price Changes Permitted!");
		}
		isPriceChangesAllowed = !isPriceChangesAllowed;
		SimpleClient.getClient().sendToServer("#changePriceChangesPolicy");
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
	void SwitchToUpdateRegulationsController(ActionEvent event) throws IOException
	{
		if(employee==null)
			return;
		
		if(employee.getType()!=3) //if you're not a customer service employee
		{
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		
		App.setRoot("regulations");
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
	@FXML
	void showRegulations(ActionEvent event) {
		String regulations = SimpleClient.getMsgString();
		setReg(regulations);
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
		try {
			SimpleClient.getClient().sendToServer("#getCurrentRegulations ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}