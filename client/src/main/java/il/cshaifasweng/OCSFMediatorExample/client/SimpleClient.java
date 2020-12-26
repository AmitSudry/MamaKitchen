package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.GetItems;

public class SimpleClient extends AbstractClient 
{
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) 
	{
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		if (msg.getClass().equals(GetItems.class)) 
		{
			EventBus.getDefault().post(new GetItemsEvent((GetItems) msg));
		}
	}
	
	public static SimpleClient getClient() 
	{
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
