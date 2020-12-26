package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ComplaintController implements Initializable 
{
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField userName;
	
    @FXML
    private void switchToPrimary() throws IOException 
    {
        App.setRoot("customer");
    }
    
   
    @FXML
    void login(ActionEvent event) 
    {
    	userName.setText("");
    	password.setText("");
    }
    
    @Subscribe
    public void onTransfetItemEvent(DeliveryEvent event) throws IOException 
    {
   
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
	}
}