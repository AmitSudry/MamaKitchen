package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
import il.cshaifasweng.OCSFMediatorExample.entities.Delivery;
import il.cshaifasweng.OCSFMediatorExample.entities.GetDeliveries;
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
	private List<Delivery> activeDeliveries;
	@FXML
	private ChoiceBox<String> chooseDelivery;
	@FXML
	private TextField status;
	private int index;
    
    @FXML
    private void SwitchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
    
    @FXML
    void ApproveDeliveryChoice(ActionEvent event) 
    {
    	
    }
    
    void refresh() {
    	try 
    	{
			SimpleClient.getClient().sendToServer("#getActiveDeliveries");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chooseDelivery.setValue(null);
    }
    
    int findIndex(String chosenDelivery) {
    	for(int i=0; i < activeDeliveries.size(); i++) {
        	Delivery currentDelivery = activeDeliveries.get(i);
        	activeDeliveries.get(i).setID(i);
        	String check = ("name: " + currentDelivery.getName() + ", phone number: " + currentDelivery.getPhoneNumber() + ", date: " + currentDelivery.getDate() + 
        			", hour: " + getCorrectHour(currentDelivery.getHour()) + ", total: " + currentDelivery.getTotal());
        	if (chosenDelivery.contains(check))
        		return i;
    	}
    	return -1;
    }
    
    @FXML
    void Cancel(ActionEvent event) {
    	if(chooseDelivery.getValue() == null) {
    		status.setText("Please choose delivery");
    		return;
    	} 
    	String chosenDelivery = chooseDelivery.getValue();
    	int index = findIndex(chosenDelivery);
    	int hour = activeDeliveries.get(index).getHour();
    	String date = activeDeliveries.get(index).getDate();
    	double total = activeDeliveries.get(index).getTotal();
    	try 
    	{
			SimpleClient.getClient().sendToServer("#removeDelivery " + index);
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	refresh();
    	String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	if(currentDate.contains(date)) {
    		TimeZone tz = TimeZone.getTimeZone("Asia/Jerusalem");
    		Calendar calendar = Calendar.getInstance();
    		int hoursNow = calendar.get(Calendar.HOUR_OF_DAY);
    		int minutesNow = calendar.get(Calendar.MINUTE);
    		//System.out.println("Hour: " + hoursNow + ", minutes: " + minutesNow);
    		int minutes = hour % 100;
    		hour /= 100;
    		if (hour - hoursNow > 3) {
    			status.setText("Delivery canceled! you got 100% back (" + total + ")");
    		} else if (hour - hoursNow == 3) {
    			if(minutes >= minutesNow)
    				status.setText("Delivery canceled! you got 100% back (" + total + ")");
    			else
    				status.setText("Delivery canceled! you got 50% back (" + total/2 + ")");
    		} else if(hour - hoursNow > 1) {
    			status.setText("Delivery canceled! you got 50% back (" + total/2 + ")");
    		} else if(hour - hoursNow == 1) {
    			if(minutes >= minutesNow)
    				status.setText("Delivery canceled! you got 50% back (" + total/2 + ")");
    			else
    				status.setText("Delivery canceled! you got 0% back (" + 0 + ")");
    		} else if(hour - hoursNow == 0) {
    			status.setText("Delivery canceled! you got 0% back (" + 0 + ")");
    		}
    	}
    		
    }
    
    private String getCorrectHour(int hour) {
    	int minutes = hour%100;
    	hour = hour/100;
    	String correctHour = "";
    	if(hour < 10) {
    		correctHour += "0";
    	}
    	correctHour += hour;
    	correctHour += ":";
    	if(minutes < 10) {
    		correctHour += "0";
    	}
    	correctHour += minutes;
    	return correctHour;
    }
    
    @Subscribe
    public void onWarningEvent(GetDeliveriesEvent event) 
    {
    	activeDeliveries = event.getDetails().getBranches(); //it's getDeliveries!
    	chooseDelivery.getItems().clear();
        ObservableList<String> list2 = chooseDelivery.getItems();
        for(int i=0; i < activeDeliveries.size(); i++) {
        	Delivery currentDelivery = activeDeliveries.get(i);
        	activeDeliveries.get(i).setID(i);
        	list2.add("name: " + currentDelivery.getName() + ", phone number: " + currentDelivery.getPhoneNumber() + ", date: " + currentDelivery.getDate() + 
        			", hour: " + getCorrectHour(currentDelivery.getHour()) + ", total: " + currentDelivery.getTotal());
        }
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		
		try 
    	{
			SimpleClient.getClient().sendToServer("#getActiveDeliveries");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}