package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.DiningTable;
import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.GetReports;
import il.cshaifasweng.OCSFMediatorExample.entities.Reservation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewOccupationController implements Initializable 
{	
	private List<Branch> branchList;
	
	@FXML
    private ComboBox<String> BranchPick;

    @FXML
    private TextArea area;

    @FXML
    private TextField ActionStatus;

	private String displayText = "";
	
    @FXML
    void ApproveBranchChoise(ActionEvent event)
    {
    	if(BranchPick.getValue()==null)
    		return;
    	if(displayText.equals(""))
			displayText = area.getText();
			
    	area.setText("");
    	area.appendText("The occupation of branch " + BranchPick.getValue().toString() + ":\n\n");
    	
    	int branchIndex = -1;
        for(int i=0; i<branchList.size(); i++)
        {
        	if(branchList.get(i).getName().equals(BranchPick.getValue().toString()))
        	{
        		branchIndex = i;
        		break;
        	}	
        }	
    	
    	for (DiningTable table: branchList.get(branchIndex).getTableList()) {
    		area.appendText(table.toString() + "\n");
    		for (Reservation r: table.getReservations()) {
    			area.appendText(r.toString() + "\n");
    		}
    		area.appendText("\n");
    	}
    }
    
	@FXML
	void SwitchToEmployeeMainController() throws IOException
	{
		App.setRoot("employeeMain");
	}
	
    @Subscribe
    public void onGetBranchesEvent(GetBranchesEvent event) //receives this event at one with "LoginController"
    {
    	BranchPick.getItems().clear();
        ObservableList<String> list = BranchPick.getItems();
        System.out.println();
        for(int i=0; i<event.getDetails().getBranches().size(); i++)
        {
        	list.add(event.getDetails().getBranches().get(i).getName());
        }	
        
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