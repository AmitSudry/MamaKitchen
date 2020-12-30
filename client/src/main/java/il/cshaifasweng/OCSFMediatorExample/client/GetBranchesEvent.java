package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.GetBranches;

public class GetBranchesEvent {
	private GetBranches details;
	
	
	public GetBranches getDetails() 
	{
		return details;
	}

	public GetBranchesEvent(GetBranches warning) 
	{
		this.details = warning;
	}
}
