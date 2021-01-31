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
	private List<Branch> branchList;
	@FXML
	private ChoiceBox<String> openHourHaifa, openMinuteHaifa, closeHourHaifa, closeMinuteHaifa;
	@FXML
	private ChoiceBox<String> openHourJerusalem, openMinuteJerusalem, closeHourJerusalem, closeMinuteJerusalem;
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
		openHourHaifa.setValue(null);
		openMinuteHaifa.setValue(null);
		closeHourHaifa.setValue(null);
		closeMinuteHaifa.setValue(null);
		openHourJerusalem.setValue(null);
		openMinuteJerusalem.setValue(null);
		closeHourJerusalem.setValue(null);
		closeMinuteJerusalem.setValue(null);
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
		
		if(openHourHaifa.getValue() != null) {
			openHourHaifaReal = Integer.parseInt(openHourHaifa.getValue());
			if(openHourHaifaReal < 0 || openHourHaifaReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;
		}
		
		if(openMinuteHaifa.getValue() != null) {
			openMinuteHaifaReal = Integer.parseInt(openMinuteHaifa.getValue());
			if(openMinuteHaifaReal < 0 || openMinuteHaifaReal > 59) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeHourHaifa.getValue() != null) {
			closeHourHaifaReal = Integer.parseInt(closeHourHaifa.getValue());
			if(closeHourHaifaReal < 0 || closeHourHaifaReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeMinuteHaifa.getValue() != null) {
			closeMinuteHaifaReal = Integer.parseInt(closeMinuteHaifa.getValue());
			if(closeMinuteHaifaReal < 0 || closeMinuteHaifaReal > 59) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(openHourJerusalem.getValue() != null) {
			openHourJerusalemReal = Integer.parseInt(openHourJerusalem.getValue());
			if(openHourJerusalemReal < 0 || openHourJerusalemReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(openMinuteJerusalem.getValue() != null) {
			openMinuteJerusalemReal = Integer.parseInt(openMinuteJerusalem.getValue());
			if(openMinuteJerusalemReal < 0 || openMinuteJerusalemReal > 59) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeHourJerusalem.getValue() != null) {
			closeHourJerusalemReal = Integer.parseInt(closeHourJerusalem.getValue());
			if(closeHourJerusalemReal < 0 || closeHourJerusalemReal > 23) {
				Status.setText("Bad input!");
				cleanInput();
				return;
			}
		} else {
			Status.setText("Bad input!");
			cleanInput();
			return;		}
		
		if(closeMinuteJerusalem.getValue() != null) {
			closeMinuteJerusalemReal = Integer.parseInt(closeMinuteJerusalem.getValue());
			if(closeMinuteJerusalemReal < 0 || closeMinuteJerusalemReal > 59) {
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
    	branchList = event.getDetails().getBranches();
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
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


		openHourHaifa.getItems().clear();
        ObservableList<String> list1 = openHourHaifa.getItems();
        openMinuteHaifa.getItems().clear();
        ObservableList<String> list2 = openMinuteHaifa.getItems();
        closeHourHaifa.getItems().clear();
        ObservableList<String> list3 = closeHourHaifa.getItems();
        closeMinuteHaifa.getItems().clear();
        ObservableList<String> list4 = closeMinuteHaifa.getItems();
        openHourJerusalem.getItems().clear();
        ObservableList<String> list5 = openHourJerusalem.getItems();
        openMinuteJerusalem.getItems().clear();
        ObservableList<String> list6 = openMinuteJerusalem.getItems();
        closeHourJerusalem.getItems().clear();
        ObservableList<String> list7 = closeHourJerusalem.getItems();
        closeMinuteJerusalem.getItems().clear();
        ObservableList<String> list8 = closeMinuteJerusalem.getItems();
        
        for(int i=0; i <= 23; i++) {
        	if(i<10) // something like 1305 yields that the minute should be 05 and not 5
        	{
        		list1.add("0" + i);
        		list3.add("0" + i);
        		list5.add("0" + i);
        		list7.add("0" + i);
        	}
        	else
        	{
        		list1.add(String.valueOf(i));
        		list3.add(String.valueOf(i));
        		list5.add(String.valueOf(i));
        		list7.add(String.valueOf(i));
        	}
        }
        for(int i=0; i <= 59; i++) {
        	if(i<10) // something like 1305 yields that the minute should be 05 and not 5
        	{
        		list2.add("0" + i);
        		list4.add("0" + i);
        		list6.add("0" + i);
        		list8.add("0" + i);
        	}
        	else
        	{
        		list2.add(String.valueOf(i));
        		list4.add(String.valueOf(i));
        		list6.add(String.valueOf(i));
        		list8.add(String.valueOf(i));
        	}
        }
	}
}