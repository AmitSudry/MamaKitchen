package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
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


public class updateRegulation implements Initializable 
{
	@FXML
	private TextField openHourHaifa, openMinuteHaifa, closeHourHaifa, closeMinuteHaifa;
	@FXML
	private TextField openHourJerusalem, openMinuteJerusalem, closeHourJerusalem, closeMinuteJerusalem;
	@FXML
	private TextField maxOpen, maxClose;
	@FXML
	private CheckBox isDelivery;
	@FXML 
	private TextField Status;
    
	@FXML
	void SwitchToEmployeeMainController() throws IOException
	{
		App.setRoot("employeeMain");
	}
	
	private boolean isNumeric(String s) {  
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	
	private void cleanInput() {
		openHourHaifa.setText("");
		openMinuteHaifa.setText("");
		closeHourHaifa.setText("");
		closeMinuteHaifa.setText("");
		openHourJerusalem.setText("");
		openMinuteJerusalem.setText("");
		closeHourJerusalem.setText("");
		closeMinuteJerusalem.setText("");
		maxOpen.setText("");
		maxClose.setText("");;
		isDelivery.setSelected(false);
	}
	
	@FXML
    void UpdateReg(ActionEvent event) throws IOException 
	{
		int openHourHaifaReal, openMinuteHaifaReal, closeHourHaifaReal, closeMinuteHaifaReal;
		int openHourJerusalemReal, openMinuteJerusalemReal, closeHourJerusalemReal, closeMinuteJerusalemReal;
		int maxOpenReal, maxCloseReal;
		boolean isDeliveryReal;
		
		if(openHourHaifa.getText() != null && isNumeric(openHourHaifa.getText())) {
			openHourHaifaReal = Integer.parseInt(openHourHaifa.getText());
			if(openHourHaifaReal < 0 && openHourHaifaReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;
		}
		
		if(openMinuteHaifa.getText() != null && isNumeric(openMinuteHaifa.getText())) {
			openMinuteHaifaReal = Integer.parseInt(openMinuteHaifa.getText());
			if(openMinuteHaifaReal < 0 && openMinuteHaifaReal > 59) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeHourHaifa.getText() != null && isNumeric(closeHourHaifa.getText())) {
			closeHourHaifaReal = Integer.parseInt(closeHourHaifa.getText());
			if(closeHourHaifaReal < 0 && closeHourHaifaReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeMinuteHaifa.getText() != null && isNumeric(closeMinuteHaifa.getText())) {
			closeMinuteHaifaReal = Integer.parseInt(closeMinuteHaifa.getText());
			if(closeMinuteHaifaReal < 0 && closeMinuteHaifaReal > 59) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(openHourJerusalem.getText() != null && isNumeric(openHourJerusalem.getText())) {
			openHourJerusalemReal = Integer.parseInt(openHourJerusalem.getText());
			if(openHourJerusalemReal < 0 && openHourJerusalemReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(openMinuteJerusalem.getText() != null && isNumeric(openMinuteJerusalem.getText())) {
			openMinuteJerusalemReal = Integer.parseInt(openMinuteJerusalem.getText());
			if(openMinuteJerusalemReal < 0 && openMinuteJerusalemReal > 59) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeHourJerusalem.getText() != null && isNumeric(closeHourJerusalem.getText())) {
			closeHourJerusalemReal = Integer.parseInt(closeHourJerusalem.getText());
			if(closeHourJerusalemReal < 0 && closeHourJerusalemReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeMinuteJerusalem.getText() != null && isNumeric(closeMinuteJerusalem.getText())) {
			closeMinuteJerusalemReal = Integer.parseInt(closeMinuteJerusalem.getText());
			if(closeMinuteJerusalemReal < 0 && closeMinuteJerusalemReal > 59) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(maxOpen.getText() != null && isNumeric(maxOpen.getText())) {
			maxOpenReal = Integer.parseInt(maxOpen.getText());
			if(maxOpenReal < 0) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(maxClose.getText() != null && isNumeric(maxClose.getText())) {
			maxCloseReal = Integer.parseInt(maxClose.getText());
			if(maxCloseReal < 0) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}

		if(isDelivery.isSelected())
		{
			isDeliveryReal = true;
		}
		else
		{
			isDeliveryReal = false;
		}
		
		SimpleClient.getClient().sendToServer("#setCurrentRegulations " + 
				String.valueOf(openHourHaifaReal) + " " + 
				String.valueOf(openMinuteHaifaReal) + " " + 
				String.valueOf(closeHourHaifaReal) + " " + 
				String.valueOf(closeMinuteHaifaReal) + " " +
				String.valueOf(openHourJerusalemReal) + " " + 
				String.valueOf(openMinuteJerusalemReal) + " " + 
				String.valueOf(closeHourJerusalemReal) + " " + 
				String.valueOf(closeMinuteJerusalemReal) + " " +
				String.valueOf(maxOpenReal) + " " + 
				String.valueOf(maxCloseReal) + " " +
				String.valueOf(isDeliveryReal));

		Status.setText("Regulations chnaged!");
		cleanInput();
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