package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.GetBranches;
import il.cshaifasweng.OCSFMediatorExample.entities.GetReports;

public class SimpleClient extends AbstractClient 
{
	
	private static SimpleClient client = null;
	private static String strMsg;

	private SimpleClient(String host, int port) 
	{
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		String msgString = msg.toString();
		if (msgString.contains("#regulations")) {
			strMsg = msgString;
		}
		else if(msg.getClass().equals(Employee.class))
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
	}
	
	public static SimpleClient getClient() 
	{
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
	
	public static String getMsgString() {
		return strMsg;
	}

}
