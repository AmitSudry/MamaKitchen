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
    private Button PreviewMenu;

    @FXML
    private Button OrderDelivery;

    @FXML
    private Button BookTable;

    @FXML
    private Button NoReservation;

    @FXML
    private Button Complaint;
    
	@FXML
    private ComboBox<String> updateChoise;
	 
	@FXML
	private TextArea area;
	
	@FXML
	private Button updateItem;
	
	@FXML
	private TextField typeText;
	
	@FXML
	private CheckBox removeBool;
	
	@FXML
	private TextField priceText;
	
	@FXML
    private Button GoBack;
	
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
    
	@FXML
    void UpdateItem(ActionEvent event) throws IOException 
	{
		if(removeBool.isSelected())
		{
			SimpleClient.getClient().sendToServer("#removeItem " + updateChoise.getValue().toString());
		}
		else
		{
			SimpleClient.getClient().sendToServer("#updateItem "  
					+ updateChoise.getValue().toString() + " "
					+ priceText.getText() + " " 
					+ typeText.getText());
		}
		
		priceText.setText("");
		typeText.setText("");
		removeBool.setSelected(false);
		
		sendShowMenu(event);
    }
	
    @FXML
    void sendShowMenu(ActionEvent event) 
    {
    	try 
    	{
			SimpleClient.getClient().sendToServer("#showMenu");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     
    
    @Subscribe
    public void onWarningEvent(GetItemsEvent event) 
    {
    	area.setText("");
    	for(int i=0; i<event.getWarning().getItems().size(); i++)
    	{	
    		area.appendText(event.getWarning().getItems().get(i).toString() + "\n");
    	}
    	
    	updateChoise.getItems().clear();
        ObservableList<String> list = updateChoise.getItems();
        
        for(int i=0; i<event.getWarning().getItems().size(); i++)
        {
        	list.add(event.getWarning().getItems().get(i).getName());
        }	
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		EventBus.getDefault().register(this);
	}
    
    


}
