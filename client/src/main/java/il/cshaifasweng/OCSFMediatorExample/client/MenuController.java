package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MenuController implements Initializable 
{
	private List<Branch> branchList;
	
	@FXML
    private TextArea Menu;
	
	@FXML
	private ComboBox<String> BranchPick;
	
    @FXML
    private void switchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
    
    @FXML
    void ApproveBranchChoise(ActionEvent event) 
    {
    	if(BranchPick.getValue()==null)
    		return;

        int branchIndex = -1;
        for(int i=0; i<branchList.size(); i++)
        {
        	if(branchList.get(i).getName().equals(BranchPick.getValue().toString()))
        	{
        		branchIndex = i;
        		break;
        	}	
        }	
        
        Menu.setText("");
        for(int i=0; i<branchList.get(branchIndex).getMenu().getItemList().size(); i++)
    	{	
    		Menu.appendText(branchList.get(branchIndex).getMenu().getItemList().get(i).toString() + "\n");
    	}
    }
    
   
    @Subscribe
    public void onWarningEvent(GetBranchesEvent event) 
    {    	
    	BranchPick.getItems().clear();
        ObservableList<String> list = BranchPick.getItems();
        
        for(int i=0; i<event.getDetails().getBranches().size(); i++)
        {
        	list.add(event.getDetails().getBranches().get(i).getName());
        }	
        
        branchList = event.getDetails().getBranches();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		
		
		try //Gather the menu info from the server 
    	{
			SimpleClient.getClient().sendToServer("#showMenu");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}