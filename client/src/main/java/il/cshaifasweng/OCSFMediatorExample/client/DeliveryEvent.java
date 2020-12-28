package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Delivery;

public class DeliveryEvent 
{
	private Delivery deliveryDetails;
	
	public Delivery getDelivery() 
	{
		return deliveryDetails;
	}

	public DeliveryEvent(Delivery delInfo) 
	{
		this.deliveryDetails = delInfo;
	}
}
