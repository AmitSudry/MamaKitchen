package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class GetBranches implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8224097662914849956L;
	
	List<Branch> branches;
	private String message;
	private LocalTime time;
	
	public GetBranches(String message, List<Branch> branches) 
	{
		super();
		this.message = message;
		this.time = LocalTime.now();
		this.branches = branches;
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
	
	public List<Branch> getBranches() 
	{
		return branches;
	}

	public void setBranches(List<Branch> branches) 
	{
		this.branches = branches;
	}
}
