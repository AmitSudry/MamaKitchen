package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

public class WarningEvent {
	private Warning warning;
	
	
	public Warning getWarning() 
	{
		return warning;
	}

	public WarningEvent(Warning warning) 
	{
		this.warning = warning;
	}
}
