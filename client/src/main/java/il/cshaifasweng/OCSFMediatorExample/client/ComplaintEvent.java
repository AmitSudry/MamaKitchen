package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;

public class ComplaintEvent 
{
	private Complaint complaintDetails;
	
	public Complaint getComplaint() 
	{
		return complaintDetails;
	}

	public ComplaintEvent(Complaint compInfo) 
	{
		this.complaintDetails = compInfo;
	}
}
