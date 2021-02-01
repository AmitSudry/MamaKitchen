package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
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
	
	private List<Branch> branchList;
	
	@FXML
	private Label EmployeeGreeting;
	 
	@FXML
	private TextField ActionStatus; //check for employee permissions
	
	@FXML
	private Label priceChangesText;
	
	@FXML
	private TextArea regulationsText;
	
	private static boolean isPriceChangesAllowed = true;
	
	public void setReg() {
		int openHourHaifa = branchList.get(0).getOpenHour();
		int openMinuteHaifa = branchList.get(0).getOpenMinute();
		int closeHourHaifa = branchList.get(0).getCloseHour();
		int closeMinuteHaifa = branchList.get(0).getCloseMinute();
		int openHourJerusalem = branchList.get(1).getOpenHour();
		int openMinuteJerusalem = branchList.get(1).getOpenMinute();
		int closeHourJerusalem = branchList.get(1).getCloseHour();
		int closeMinuteJerusalem = branchList.get(1).getCloseMinute();
		int maxOpen = branchList.get(0).getMaxOpen();
		int maxClose = branchList.get(0).getMaxClose();
		boolean isDelivery = branchList.get(0).getIsDelivery();
		
		
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
    void SwitchToAssignTableController(ActionEvent event) throws IOException 
	{
		if(employee==null)
			return;
		
		if(employee.getType()!=1) //if you're not the hostess
		{
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		
		App.setRoot("reservation");
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
	void SwitchToViewReportsController(ActionEvent event) throws IOException 
	{
		if(employee==null)
			return;
		
		if(employee.getType()==5) //not the chain manager
		{
			App.setRoot("viewReportsChain");
		}
		else if(employee.getType() == 4) {
			App.setRoot("viewReportsBranch");
		} 
		else {
			ActionStatus.setText("You are not authorized for this action!");
			return;
		}
		
	}
	@FXML
	void showRegulations(ActionEvent event) {
		setReg();
	}
	
    @Subscribe
    public void onEmployeeEvent(Employee event) //receives this event at one with "LoginController"
    {
    	this.employee = event;
    	EmployeeGreeting.setText("Hello " + employee.getUserName());
    }
    
    @Subscribe
    public void onWarningEvent(GetBranchesEvent event) 
    {
        
        branchList = event.getDetails().getBranches();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		try 
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