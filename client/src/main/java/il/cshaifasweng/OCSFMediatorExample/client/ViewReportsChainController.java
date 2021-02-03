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

public class ViewReportsChainController implements Initializable 
{	
	private List<Branch> branches;

    @FXML
    private TextArea area;

    @FXML
    private TextField ActionStatus;
    
	@FXML
	void SwitchToEmployeeMainController() throws IOException
	{
		App.setRoot("employeeMain");
	}
	
	@FXML
	void showReportClicked() {
		int totalDelivery = 0;
		int totalTA = 0;
		int totalReservations = 0;
		int totalReject = 0;
		for(int i=0; i < branches.size(); i++) {
			totalDelivery += branches.get(i).getDeliveryCounter();
			totalTA += branches.get(i).getTakeawayCounter();
			totalReservations += branches.get(i).getReservationCounter();
			totalReject += branches.get(i).getRejectedCustomersCounter();
		}
		String report = "Report for mama's kitchen chain:\n" +
				totalDelivery + " deliveries ordered this month\n" +
				totalTA + " takeaways ordered this month\n" +
				totalReservations + " place reservations ordered this month\n" +
				totalReject + " people rejected because of the coronavirus rules\n" + 
				branches.get(0).getComplaintsToal() + " complaints sent to us and we handled " + branches.get(0).getComplaintsClosed() + " of them";
		area.setText(report);
	}
	
	@Subscribe
    public void onWarningEvent(GetBranchesEvent event) 
    {
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