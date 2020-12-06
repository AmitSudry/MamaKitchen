package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.TransferItem;

public class TransferItemEvent 
{
	private TransferItem Item;
	
	public TransferItem getTransferItem() 
	{
		return Item;
	}

	public TransferItemEvent(TransferItem item) 
	{
		this.Item = item;
	}
}
