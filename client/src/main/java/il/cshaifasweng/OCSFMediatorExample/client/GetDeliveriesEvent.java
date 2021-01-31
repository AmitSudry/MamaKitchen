package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.GetBranches;
import il.cshaifasweng.OCSFMediatorExample.entities.GetDeliveries;

public class GetDeliveriesEvent {
	private GetDeliveries details;
	
	
	public GetDeliveries getDetails() 
	{
		return details;
	}

	public GetDeliveriesEvent(GetDeliveries warning) 
	{
		this.details = warning;
	}
}
