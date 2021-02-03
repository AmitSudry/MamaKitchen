package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.GetBranches;
import il.cshaifasweng.OCSFMediatorExample.entities.GetDeliveries;
import il.cshaifasweng.OCSFMediatorExample.entities.GetReservation;

public class GetReservationEvent {
	private GetReservation details;
	
	
	public GetReservation getDetails() 
	{
		return details;
	}

	public GetReservationEvent(GetReservation warning) 
	{
		this.details = warning;
	}
}
