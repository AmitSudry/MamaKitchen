package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
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
	
	//private List<Branch> branchList;
	
	@FXML
    private DatePicker Date;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> Branch;

    @FXML
    private ComboBox<String> Hour;

    @FXML
    private TextField NumberOfPeople;

    @FXML
    private TextField Name;

    @FXML
    private ComboBox<String> Dining;

    @FXML
    private Button BookTable;

    @FXML
    private TextField ReservationStatus;

    @FXML
    private Button GoBack;

    @FXML
    void BookTable(ActionEvent event) 
    {
    	ReservationStatus.setText("");
    	ReservationStatus.setText("Table booked!");
    }
    
	@FXML
    private void switchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
	
	@Subscribe
    public void onWarningEvent(GetItemsEvent event) 
    {
    
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		
		Dining.getItems().clear();
        ObservableList<String> list = Dining.getItems();
        
        list.add("Inside");
        list.add("Outside");	
	}
}