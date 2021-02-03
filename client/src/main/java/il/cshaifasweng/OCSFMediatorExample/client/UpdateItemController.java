package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
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

public class UpdateItemController implements Initializable 
{
	private List<Branch> branchList;
	
    @FXML
    private ComboBox<String> BranchPick;

    @FXML
    private TextArea area;

    @FXML
    private ComboBox<String> ItemPick;
    
    @FXML 
    private TextField nameText;
    
    @FXML
    private TextField typeText;
    
    @FXML
    private TextField ingridientsText;

    @FXML
    private CheckBox removeBool;

    @FXML
    private TextField priceText;
	
    @FXML
    private TextField Status;
    
	@FXML
	void SwitchToEmployeeMainController() throws IOException
	{
		App.setRoot("employeeMain");
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
        
       
        ItemPick.getItems().clear();
        ObservableList<String> list = ItemPick.getItems();
        
        list.add("add new item");
        
    	area.setText("");
    	for(int i=0; i<branchList.get(branchIndex).getMenu().getItemList().size(); i++)
    	{	
    		area.appendText(branchList.get(branchIndex).getMenu().getItemList().get(i).toString() + "\n");
    		list.add(branchList.get(branchIndex).getMenu().getItemList().get(i).getName());
    	}
    }
    
	@FXML
    void UpdateItem(ActionEvent event) throws IOException 
	{
		if(BranchPick.getValue()==null || ItemPick.getValue()==null)
			return;

		if(removeBool.isSelected())
		{
			SimpleClient.getClient().sendToServer("#removeItem " + ItemPick.getValue().toString() +  " " + BranchPick.getValue().toString());
		}
		else
		{
			if(typeText.getText().equals(""))
			{
				Status.setText("You're missing fields!");
				return;
			}
			else
			{
				if(ingridientsText.getText().equals("")) {
					ingridientsText.setText("miss");
				}
				if(priceText.getText().equals("")) {
					priceText.setText("miss");
				} else {
					try 
			    	{
			    		Float.parseFloat(priceText.getText());
			        } 
			    	catch (final NumberFormatException e) 
			    	{
			    		Status.setText("Invalid new price!");
			    		priceText.setText("");
			    		return;
			        }
				}
			}
			if("add new item" == ItemPick.getValue().toString()) {
				if(priceText.getText().contains("miss")) {
					Status.setText("Invalid price for new product!");
					priceText.setText("");
					return;
				}
				if(ingridientsText.getText().contains("miss")) {
					Status.setText("Invalid ingridients for new product!");
					ingridientsText.setText("");
					return;
				}
				SimpleClient.getClient().sendToServer("#addItem "  
						+ nameText.getText() + " "
						+ typeText.getText() + " " 
						+ ingridientsText.getText() + " "
						+ priceText.getText() + " " 
						+ BranchPick.getValue().toString());
			} else {
				if(ingridientsText.getText().contains("miss")) {
					Status.setText("Invalid ingridients for the product!");
					ingridientsText.setText("");
					return;
				}
				SimpleClient.getClient().sendToServer("#updateItem "  
						+ ItemPick.getValue().toString() + " "
						+ priceText.getText() + " " 
						+ typeText.getText() + " " 
						+ BranchPick.getValue().toString() + " "
						+ ingridientsText.getText());
			}
		}
		
		nameText.setText("");
		priceText.setText("");
		typeText.setText("");
		ingridientsText.setText("");
		removeBool.setSelected(false);
		area.setText("");
		
		try 
    	{
			SimpleClient.getClient().sendToServer("#showMenu");
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BranchPick.setValue(null);
		ItemPick.setValue(null);

		Status.setText("Update sent for approval!");
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
	}
}
