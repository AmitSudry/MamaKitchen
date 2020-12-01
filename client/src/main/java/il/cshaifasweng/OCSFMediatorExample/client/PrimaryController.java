package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController 
{

    @FXML
    void sendShowMenu(ActionEvent event) 
    {
    	//System.out.println("im inside");
    	try 
    	{
    		//System.out.println("im inside2");
			SimpleClient.getClient().sendToServer("#showMenu");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
