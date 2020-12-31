package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Reservation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ReservationController implements Initializable 
{
	
	private List<Branch> branchList;
	
	private int openingHour;
	private int closingHour;
	
	@FXML
    private TextField Phone;
	
	@FXML
    private DatePicker Date;

    @FXML
    private ComboBox<String> BranchPick;

    @FXML
    private ComboBox<String> HourPick;
    
    @FXML
    private ComboBox<String> MinutesPick;

    @FXML
    private TextField NumberOfPeople;

    @FXML
    private TextField Name;

    @FXML
    private ComboBox<String> DiningPick;

    @FXML
    private TextField ReservationStatus;

    @FXML
    void ApproveBranchChoise(ActionEvent event)
    {
    	HourPick.setValue(null);
    	MinutesPick.setValue(null);
    	
    	if(BranchPick.getValue()==null)
    		return;
    	
        int branchIndex = -1;
        for(int i=0; i<branchList.size(); i++)
        {
        	if(branchList.get(i).getName().equals(BranchPick.getValue().toString()))
        	{
        		branchIndex = i;
        		break;
        	}	
        }	
        
        HourPick.getItems().clear();
        ObservableList<String> list2 = HourPick.getItems();
        MinutesPick.getItems().clear();
        ObservableList<String> list3 = MinutesPick.getItems();
        
        int open = branchList.get(branchIndex).getOpeningHour();
        int close = branchList.get(branchIndex).getClosingHour();
        
        openingHour = open;
        closingHour = close;
        System.out.println("from " + openingHour + " to " + closingHour);
        while(open <= close-100) //reservation could be made from opening hour to one hour before closing
        {
        	int hour = open/100;
        	if(hour<10) 
        	{
        		if(!list2.contains("0" + hour))
        			list2.add("0" + hour);
        	}
        	else
        	{
        		if(!list2.contains(String.valueOf(hour)))
        			list2.add(String.valueOf(hour));
        	}

        	open += 100; 	
        }
        
        for(int i=0; i<60; i+=15)
        {
        	if(i<10) 
        	{
        		list3.add("0" + i);
        	}
        	else
        	{
        		list3.add(String.valueOf(i));
        	}
        }
    }
    
    @FXML
    void BookTable(ActionEvent event) 
    {
    	if(Phone.getText().equals("") || Name.getText().equals("")
    			|| NumberOfPeople.getText().equals("") || DiningPick.getValue()==null
    			|| HourPick.getValue()==null || MinutesPick.getValue()==null 
    			|| BranchPick.getValue()==null || Date.getValue()==null)
    			
    	{
    		ReservationStatus.setText("You're missing fields!");
    		return;
    	}
    	
    	int requestedHour = Integer.parseInt(HourPick.getValue().toString())*100 + Integer.parseInt(MinutesPick.getValue().toString());
    	
    	if(requestedHour < openingHour || requestedHour > closingHour-100) //wrong hour
    	{
    		String from = "", to = "";
    		int hour = openingHour/100;
        	if(hour<10) 
        		from += "0" + hour;
        	else
        		from += String.valueOf(hour);
        	from += ":";
        	int minutes = openingHour%100;
        	if(minutes<10) 
        		from += "0" + minutes;
        	else
        		from += String.valueOf(minutes);
        	
        	hour = (closingHour-100)/100; //one hour before closing
        	if(hour<10) 
        		to += "0" + hour;
        	else
        		to += String.valueOf(hour);
        	to += ":";
        	minutes = closingHour%100;
        	if(minutes<10) 
        		to += "0" + minutes;
        	else
        		to += String.valueOf(minutes);
        	
        	ReservationStatus.setText("You can book a table from " + from + " to " + to);
    		return;
    	}
    	
    	int numOfPeople = -1;
    	try 
    	{
           numOfPeople = Integer.parseInt(NumberOfPeople.getText());
        } 
    	catch (final NumberFormatException e) 
    	{
    		ReservationStatus.setText("Invalid number of people!");
    		return;
        }
    	
    	boolean isInside = DiningPick.getValue().toString().equals("Inside") ? true : false;
    	
    	Reservation r = new Reservation(BranchPick.getValue().toString(), Date.getValue().toString(),
    			numOfPeople, Integer.parseInt(HourPick.getValue().toString()),
    			Name.getText(), isInside, Phone.getText());
    	
    	Phone.setText("");
    	Name.setText("");
    	NumberOfPeople.setText("");
    	HourPick.setValue(null);
    	DiningPick.setValue(null);
    	BranchPick.setValue(null);
    	Date.setValue(null);
    	
    	try 
    	{
        	SimpleClient.getClient().sendToServer(r);
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	ReservationStatus.setText("Table booked!");
    }
    
	@FXML
    private void switchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
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
		
		DiningPick.getItems().clear();
        ObservableList<String> list = DiningPick.getItems();
        
        list.add("Inside");
        list.add("Outside");
	}
}