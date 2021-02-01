package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.GetReports;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewComplaintsController implements Initializable 
{	
	private List<Complaint> complaints;
	
	@FXML
    private ComboBox<String> ComplaintPick;
	
	@FXML
    private CheckBox isRefund;

    @FXML
    private TextArea area;

    @FXML
    private TextField ActionStatus;

	
    @FXML
    void ApproveBranchChoise(ActionEvent event)
    {	
    	if(ComplaintPick.getValue()==null)
    		return;
    	int index = Integer.parseInt(ComplaintPick.getValue().toString()) - 1;
    	
    	area.setText("");
    	area.appendText("Customer Name: " + complaints.get(index).getName() + "\n\n");
    	area.appendText("=======================================" + "\n\n");
		area.appendText(complaints.get(index).getComplaint() + "\n\n");
		area.appendText("=======================================" + "\n\n");
		
		if (complaints.get(index).isHandled()) {
			String refund = complaints.get(index).isRefunded() ? "with a refund.\n" : "without a refund.\n";
			area.appendText("This complaint is handled " + refund);
		}
		else {
			area.appendText("This complaint isn't handled yet.\n");
		}
    }
    
    @FXML
    void HandleComplaint(ActionEvent event) {
    	if(ComplaintPick.getValue()==null)
    		return;
    	int index = Integer.parseInt(ComplaintPick.getValue().toString()) - 1;
    	
    	if (complaints.get(index).isHandled()) {
    		ActionStatus.setText("The complaint is alredy handled!");
    		return;
    	}
    	
    	complaints.get(index).Handle(isRefund.isSelected());
    	
    	try 
    	{
        	SimpleClient.getClient().sendToServer(complaints.get(index));
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ActionStatus.setText("The complaint is handled!");
    	
    	isRefund.setSelected(false);
    	ApproveBranchChoise(event);
    }
    
	@FXML
	void SwitchToEmployeeMainController() throws IOException
	{
		App.setRoot("employeeMain");
	}
	
    @Subscribe
    public void onReportsEvent(GetReports event) //receives this event at one with "LoginController"
    {
    	this.complaints = event.getComplaints();
    	System.out.println(complaints.size());
    	
    	ComplaintPick.setValue(null);
    	
    	ComplaintPick.getItems().clear();
        ObservableList<String> list = ComplaintPick.getItems();
        
    	for(int i=0; i<complaints.size(); i++)
    	{	
    		list.add(String.valueOf(i+1));
    	}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		
		try 
    	{
			SimpleClient.getClient().sendToServer("#reports");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}