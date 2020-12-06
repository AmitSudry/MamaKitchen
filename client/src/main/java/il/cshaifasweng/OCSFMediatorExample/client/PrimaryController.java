package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

public class PrimaryController implements Initializable 
{
	 @FXML
	 private ChoiceBox<String> updateChoise;
	 
	 @FXML
	 private TextArea area;
	 
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
    @FXML
    void onUpdateChoiseClick(MouseEvent event) 
    {
    	System.out.println(event.getSource().toString());
    }
    
    @Subscribe
    public void onWarningEvent(WarningEvent event) 
    {
    	area.setText(event.getWarning().getItems().toString());
    	updateChoise.setValue("English");
        //Retrieving the observable list
        ObservableList<String> list = updateChoise.getItems();
        //Adding items to the list
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
