package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Delivery;

public class DeliveryEvent 
{
	private DeliveryEvent deliveryDetails;
	
	public DeliveryEvent getTransferItem() 
	{
		return deliveryDetails;
	}

	public DeliveryEvent(DeliveryEvent delInfo) 
	{
		this.deliveryDetails = delInfo;
	}
}
