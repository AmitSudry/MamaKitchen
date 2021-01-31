package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.GetBranches;
import il.cshaifasweng.OCSFMediatorExample.entities.GetDeliveries;
import il.cshaifasweng.OCSFMediatorExample.entities.GetReports;

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
		if(msg.getClass().equals(Employee.class))
		{
			EventBus.getDefault().post((Employee) msg);
		}
		else if (msg.getClass().equals(GetBranches.class)) 
		{
			EventBus.getDefault().post(new GetBranchesEvent((GetBranches) msg));
		}
		else if (msg.getClass().equals(GetReports.class)) 
		{
			EventBus.getDefault().post((GetReports) msg);
		}
		else if (msg.getClass().equals(GetDeliveries.class)) 
		{
			EventBus.getDefault().post(new GetDeliveriesEvent((GetDeliveries) msg));
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
