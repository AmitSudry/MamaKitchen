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

public class ViewReportsBranchController implements Initializable 
{	
	private List<Branch> branches;
	private int branchIndex;
	
	@FXML
	private ComboBox<String> BranchPick;
		
    @FXML
    private TextArea area;

    @FXML
    private TextField ActionStatus;

	
    @FXML
    void ApproveBranchChoise(ActionEvent event)
    {	
    	if(BranchPick.getValue()==null)
    		return;
    	branchIndex = -1;
        for(int i=0; i<branches.size(); i++)
        {
        	if(branches.get(i).getName().equals(BranchPick.getValue().toString()))
        	{
        		branchIndex = i;
        		break;
        	}	
        }
        Branch branch = branches.get(branchIndex);
        String report = "Report for " + branch.getName() + " branch:\n" +
        				branch.getDeliveryCounter() + " deliveries ordered this month\n" +
        				branch.getTakeawayCounter() + " takeaways ordered this month\n" +
        				branch.getReservationCounter() + " place reservations ordered this month\n" +
        				branch.getRejectedCustomersCounter() + " people rejected because of the coronavirus rules\n" + 
        				branch.getComplaintsToal() + " complaints sent to us and we handled " + branch.getComplaintsClosed() + " of them";
        area.setText(report);
    }
    
	@FXML
	void SwitchToEmployeeMainController() throws IOException
	{
		App.setRoot("employeeMain");
	}
	
	@Subscribe
    public void onWarningEvent(GetBranchesEvent event) 
    {
    	BranchPick.getItems().clear();
        ObservableList<String> list = BranchPick.getItems();
        
        for(int i=0; i<event.getDetails().getBranches().size(); i++)
        {
        	list.add(event.getDetails().getBranches().get(i).getName());
        }	
        
        branches = event.getDetails().getBranches();
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