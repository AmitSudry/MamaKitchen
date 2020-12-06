package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SecondaryController implements Initializable 
{

    @FXML
    private void switchToPrimary() throws IOException 
    {
        App.setRoot("primary");
    }
    
    @Subscribe
    public void onTransfetItemEvent(TransferItemEvent event) throws IOException 
    {
    	if(event.getTransferItem().isRemove())
    	{
    		try //remove item and its name
        	{
    			SimpleClient.getClient().sendToServer("#removeItem " + event.getTransferItem().getItemNameToUpdate());
    		} 
        	catch (IOException e) 
        	{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	else 
    	{
    		SimpleClient.getClient().sendToServer("#updateItem "  
    				+ event.getTransferItem().getItemNameToUpdate()  
    				+ String.valueOf(event.getTransferItem().getNewPrice()) 
    				+ event.getTransferItem().getNewType());
    	}
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
	}
}