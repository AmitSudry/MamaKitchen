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

public class DeliveryController implements Initializable 
{
	private Delivery cart;
	
	private List<Item> menuItems;
	
	private List<Branch> branchList;
	
	private int openHour;
	private int closeHour;
	private int openMinute;
	private int closeMinute;
	
	@FXML
	private ComboBox<String> BranchPick;
	
	@FXML
    private ComboBox<String> ItemsPick;
	
	@FXML
	private TextArea SelectedItems;
	 
    @FXML
    private ChoiceBox<String> TAOrDeliveryPick;

    @FXML
    private DatePicker DatePick;

    @FXML
    private ChoiceBox<String> HourPick;
    
    @FXML
    private ChoiceBox<String> MinutesPick;
    
    @FXML
    private TextField Address;

    @FXML
    private TextField Name;

    @FXML
    private TextField Phone;

    @FXML
    private TextField Total;

    @FXML
    private ChoiceBox<String> PaymentPick;

    @FXML
    private TextField OrderStatus;
    
    @FXML
    private void SwitchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
    
    @FXML
    void ApproveBranchChoise(ActionEvent event) 
    {
    	if(BranchPick.getValue()==null)
    		return;
    	
    	ItemsPick.getItems().clear();
        ObservableList<String> list = ItemsPick.getItems();
        
        int branchIndex = -1;
        for(int i=0; i<branchList.size(); i++)
        {
        	if(branchList.get(i).getName().equals(BranchPick.getValue().toString()))
        	{
        		branchIndex = i;
        		break;
        	}	
        }	
        
        for(int i=0; i<branchList.get(branchIndex).getMenu().getItemList().size(); i++)
        {
        	list.add(branchList.get(branchIndex).getMenu().getItemList().get(i).getName());
        }
        
        menuItems = branchList.get(branchIndex).getMenu().getItemList();
        
        HourPick.getItems().clear();
        ObservableList<String> list2 = HourPick.getItems();
        MinutesPick.getItems().clear();
        ObservableList<String> list3 = MinutesPick.getItems();
        
        openHour = branchList.get(branchIndex).getOpenHour();
        closeHour = branchList.get(branchIndex).getCloseHour();
        openMinute = branchList.get(branchIndex).getOpenMinute();
        closeMinute = branchList.get(branchIndex).getCloseMinute();
        
        while(openHour * 100 + openMinute <= closeHour * 100 + closeMinute)
        {
        	if(openHour<10) // something like 855 yields that the hour should be 08 and not 8
        	{
        		if(!list2.contains("0" + openHour))
        			list2.add("0" + openHour);
        	}
        	else
        	{
        		if(!list2.contains(String.valueOf(openHour)))
        			list2.add(String.valueOf(openHour));
        	}

        	openHour += 1; //delivery only every 30 minutes 	
        }
        
        for(int i=0; i<60; i++)
        {
        	if(i<10) // something like 1305 yields that the minute should be 05 and not 5
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
    void ConfirmDelivery(ActionEvent event) 
    {
    	if(cart.getItemsList().size()==0)
    	{
    		OrderStatus.setText("Your cart is empty!");
    		return;
    	}
    	
    	if(Address.getText().equals("") || Name.getText().equals("")
    			|| Phone.getText().equals("") || DatePick.getValue()==null
    			|| HourPick.getValue()==null || PaymentPick.getValue()==null
    			|| MinutesPick.getValue()==null || TAOrDeliveryPick.getValue() == null)
    	{
    		OrderStatus.setText("You are missing fields!");
    		return;
    	}
    	
    	int requestedHour = Integer.parseInt(HourPick.getValue().toString())*100 + Integer.parseInt(MinutesPick.getValue().toString());
    	
    	if(requestedHour < openHour * 100 + openMinute || requestedHour > closeHour * 100 + closeMinute) //wrong hour
    	{
    		String from = "", to = "";
        	if(openHour<10) 
        		from += "0" + openHour;
        	else
        		from += String.valueOf(openHour);
        	from += ":";
        	if(openMinute<10) 
        		from += "0" + openMinute;
        	else
        		from += String.valueOf(openMinute);
        	
        	if(closeHour<10) 
        		to += "0" + closeHour;
        	else
        		to += String.valueOf(closeHour);
        	to += ":";
        	if(closeMinute<10) 
        		to += "0" + closeMinute;
        	else
        		to += String.valueOf(closeMinute);
        	
    		OrderStatus.setText("Branch is open from " + from + " to " + to);
    		return;
    	}
    	
    	cart.setAddress(Address.getText());
    	cart.setName(Name.getText());
    	cart.setPhoneNumber(Phone.getText());
    	boolean isTA = TAOrDeliveryPick.getValue().toString().equals("TakeAway") ? true : false;
    	cart.setTA(isTA);
    	cart.setHour(Integer.parseInt(HourPick.getValue().toString() + MinutesPick.getValue().toString()));
    	boolean isCredit = PaymentPick.getValue().toString().equals("Credit") ? true : false;
    	cart.setCreditCard(isCredit);
    	cart.setDate(DatePick.getValue().toString());
    	double total = Double.parseDouble(Total.getText());
    	
    	OrderStatus.setText("Your order has been accepted!");
    	if(!isTA)
    	{
    		total += 15.0; //deliveryFee
    		OrderStatus.appendText(" Delivery fee included(15.0)!");
    	}
    	cart.setTotal(total);
    	
    	
    	Address.setText("");
    	Name.setText("");
    	Phone.setText("");
    	DatePick.setValue(null);
    	HourPick.setValue(null);
    	MinutesPick.setValue(null);
    	PaymentPick.setValue(null);
    	ItemsPick.setValue(null);
    	TAOrDeliveryPick.setValue(null);
    	Total.setText("");
    	SelectedItems.setText("");
    	
    	try 
    	{
        	SimpleClient.getClient().sendToServer(cart);
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	cart = new Delivery();
		List<Item> l = new ArrayList<Item>();
		cart.setItemsList(l);
    }

    @FXML
    void AddItemToCart(ActionEvent event) 
    {
    	if(ItemsPick.getValue()==null)
    		return;
    	
		for(int i=0; i<menuItems.size(); i++)
		{
			if(menuItems.get(i).getName().equals(ItemsPick.getValue().toString())) //Found target item to add to cart
			{
				cart.getItemsList().add(menuItems.get(i));
				break;
			}
		}

		SelectedItems.setText("");
		double total = 0;
    	for(int i=0; i<cart.getItemsList().size(); i++)
    	{
    		total += cart.getItemsList().get(i).getPrice();
    		SelectedItems.appendText(cart.getItemsList().get(i).toString() + "\n");
    	}	
    	
    	Total.setText(String.valueOf(total));
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