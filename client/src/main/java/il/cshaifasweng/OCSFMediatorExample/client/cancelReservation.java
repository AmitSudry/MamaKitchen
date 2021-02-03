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
import il.cshaifasweng.OCSFMediatorExample.entities.Reservation;
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

public class cancelReservation implements Initializable 
{
	private List<Reservation> activeReservations;
	@FXML
	private ChoiceBox<String> chooseReservation;
	@FXML
	private TextField status;
	private int index;
    
    @FXML
    private void SwitchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
    
    @FXML
    void ApproveReservationChoice(ActionEvent event) 
    {
    	
    }
    
    void refresh() {
    	try 
    	{
			SimpleClient.getClient().sendToServer("#getActiveReservations");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	chooseReservation.setValue(null);
    }
    
    int findIndex(String choosenReservation) {
    	for(int i=0; i < activeReservations.size(); i++) {
    		Reservation currentReservation = activeReservations.get(i);
        	String check = ("Branch: " + currentReservation.getBranch() + ", name: "  + currentReservation.getName() + ", phone number: " 
        			+ currentReservation.getPhoneNumber() + ", date: " + currentReservation.getDate() + 
        			", hour: " + getCorrectHour(currentReservation.getHour()) + ", number of people: "
        			+ currentReservation.getNumOfPeople() + ", table: " + currentReservation.getTableId());
        	if (choosenReservation.contains(check))
        		return i;
    	}
    	return -1;
    }
    
    @FXML
    void Cancel(ActionEvent event) {
    	if(chooseReservation.getValue() == null) {
    		status.setText("Please choose reservation");
    		return;
    	} 
    	String choosenReservation = chooseReservation.getValue();
    	int index = findIndex(choosenReservation);
    	int hour = activeReservations.get(index).getHour();
    	String date = activeReservations.get(index).getDate();
    	double numOfPeople = activeReservations.get(index).getNumOfPeople();
    	try 
    	{
			SimpleClient.getClient().sendToServer("#removeReservation " + index);
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
    		int minutes = hour % 100;
    		hour /= 100;
    		if (hour - hoursNow > 1) {
    			status.setText("Reservation canceled! you weren't charged");
    		} else if (hour - hoursNow == 1) {
    			if(minutes >= minutesNow)
    				status.setText("Reservation canceled! you weren't charged");
    			else
    				status.setText("Reservation canceled! we charged " + numOfPeople*10);
    		} else if(hour - hoursNow == 0) {
    			status.setText("Reservation canceled! we charged " + numOfPeople*10);
    		}
    	} else {
    		status.setText("Reservation canceled! you weren't charged");
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
    public void onWarningEvent(GetReservationEvent event) 
    {
    	activeReservations = event.getDetails().getReservation(); 
    	chooseReservation.getItems().clear();
        ObservableList<String> list2 = chooseReservation.getItems();
        for(int i=0; i < activeReservations.size(); i++) {
        	Reservation currentReservation = activeReservations.get(i);
        	list2.add("Branch: " + currentReservation.getBranch() + ", name: "  + currentReservation.getName() + ", phone number: " 
        			+ currentReservation.getPhoneNumber() + ", date: " + currentReservation.getDate() + 
        			", hour: " + getCorrectHour(currentReservation.getHour()) + ", number of people: "
        			+ currentReservation.getNumOfPeople() + ", table: " + currentReservation.getTableId());
        }
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		
		try 
    	{
			SimpleClient.getClient().sendToServer("#getActiveReservations");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}