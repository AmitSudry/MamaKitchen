package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    void ConfirmDelivery(ActionEvent event) 
    {
    	
    	if(cart.getItemsList().size()==0)
    	{
    		OrderStatus.setText("Order Status: Your cart is empty!");
    		return;
    	}
    	
    	if(Address.getText().equals("") || Name.getText().equals("")
    			|| Phone.getText().equals("") || DatePick.getValue()==null
    			|| HourPick.getValue()==null || PaymentPick.getValue()==null
    			|| MinutesPick.getValue()==null)
    	{
    		OrderStatus.setText("Order Status: You are missing fields!");
    		return;
    	}
    	
    	OrderStatus.setText("Order Status: Your order has been accepted!");
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
    public void onWarningEvent(GetItemsEvent event) 
    {
    	ItemsPick.getItems().clear();
        ObservableList<String> list = ItemsPick.getItems();
        
        for(int i=0; i<event.getWarning().getItems().size(); i++)
        {
        	list.add(event.getWarning().getItems().get(i).getName());
        }	
        
        menuItems = event.getWarning().getItems();
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
        
        HourPick.getItems().clear();
        ObservableList<String> list2 = HourPick.getItems();
        
        list2.add("08");
        list2.add("09");
        list2.add("10");
        list2.add("11");
        list2.add("12");
        list2.add("13");
        list2.add("14");
        list2.add("15");
        
        MinutesPick.getItems().clear();
        ObservableList<String> list3 = MinutesPick.getItems();
        
        list3.add("00");
        list3.add("15");
        list3.add("30");
        list3.add("45");
	}
}