package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
import il.cshaifasweng.OCSFMediatorExample.entities.Delivery;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class cancelDelivery implements Initializable 
{
	@FXML
	private ChoiceBox<String> chooseDelivery;
	@FXML
	private TextField status;
    
    @FXML
    private void SwitchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
    
    @FXML
    void ApproveDeliveryChoice(ActionEvent event) 
    {
    	
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
		
		cart = new Delivery();
		List<Item> l = new ArrayList<Item>();
		cart.setItemsList(l);
		
		PaymentPick.getItems().clear();
        ObservableList<String> list = PaymentPick.getItems();
        
        list.add("Cash");
        list.add("Credit");
        
        TAOrDeliveryPick.getItems().clear();
        ObservableList<String> list1 = TAOrDeliveryPick.getItems();
        
        list1.add("TakeAway");
        list1.add("Delivery");
	}
}