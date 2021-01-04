package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

//import il.cshaifasweng.OCSFMediatorExample.entities.TransferItemEvent;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

public class CustomerController implements Initializable 
{

	@FXML
    private ComboBox<String> updateChoise;
	 
	@FXML
	private TextArea area;
	
	@FXML
	private TextField typeText;
	
	@FXML
	private CheckBox removeBool;
	
	@FXML
	private TextField priceText;
	
	@FXML
	void SwitchToLoginController() throws IOException
	{
		App.setRoot("login");
	}
	
	@FXML
    void SwitchToComplaintController() throws IOException
	{
		App.setRoot("complaint");
    }

    @FXML
    void SwitchToDeliveryController() throws IOException
    {
    	App.setRoot("delivery");
    }

    @FXML
    void SwitchToMenuController() throws IOException
    {
    	App.setRoot("menu");
    }

    @FXML
    void SwitchToReservationController() throws IOException
    {
    	App.setRoot("reservation");
    }   
    
    @Subscribe
    public void onWarningEvent(GetBranchesEvent event) 
    {

    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		EventBus.getDefault().register(this);
	}
}
