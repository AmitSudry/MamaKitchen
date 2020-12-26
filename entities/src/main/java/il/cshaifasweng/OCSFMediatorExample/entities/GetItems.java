package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class GetItems implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8224097662914849956L;
	
	List<Item> items;
	private String message;
	private LocalTime time;
	
	public GetItems(String message, List<Item> items) 
	{
		super();
		this.message = message;
		this.time = LocalTime.now();
		this.items = items;
	}
		
	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message = message;
	}

	public LocalTime getTime() 
	{
		return time;
	}
	
	public List<Item> getItems() 
	{
		return items;
	}

	public void setItems(List<Item> items) 
	{
		this.items = items;
	}
}
