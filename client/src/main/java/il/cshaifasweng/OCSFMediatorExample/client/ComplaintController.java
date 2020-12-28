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

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;

public class ComplaintController implements Initializable 
{
	@FXML
    private TextField Name;

    @FXML
    private TextArea Complaint;

    @FXML
    private TextField ComplaintStatus;

    @FXML
    void SwitchToCustomerController(ActionEvent event) throws IOException 
    {
    	App.setRoot("customer");
    }
    
    @FXML
    void FileComplaint(ActionEvent event) 
    {
    	if(Name.getText().equals(""))
    	{
    		ComplaintStatus.setText("You must enter your name!");
    		return;
    	}
    	
    	if(Complaint.getText().equals("") || Complaint.getText().equals("Please type in your compaint"))
    	{
    		ComplaintStatus.setText("You must type in your complaint!");
    		return;
    	}
    	
    	Complaint c = new Complaint(Name.getText(), Complaint.getText());
    	Complaint.setText("");
    	Name.setText("");
    	
    	try 
    	{
        	SimpleClient.getClient().sendToServer(c);
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ComplaintStatus.setText("Your complaint has been sent!");
    }

    @Subscribe
    public void onTransfetItemEvent(DeliveryEvent event) throws IOException 
    {
   
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
	}
}