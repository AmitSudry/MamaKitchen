package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class GetReports implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8224097662914849956L;
	
	List<Complaint> complaints;
	private String message;
	private LocalTime time;
	
	public GetReports(String message, List<Complaint> complaints) 
	{
		super();
		this.message = message;
		this.time = LocalTime.now();
		this.complaints = complaints;
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
	
	public List<Complaint> getComplaints() 
	{
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) 
	{
		this.complaints = complaints;
	}
}
