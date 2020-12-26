package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.GetItems;

public class GetItemsEvent {
	private GetItems warning;
	
	
	public GetItems getWarning() 
	{
		return warning;
	}

	public GetItemsEvent(GetItems warning) 
	{
		this.warning = warning;
	}
}
