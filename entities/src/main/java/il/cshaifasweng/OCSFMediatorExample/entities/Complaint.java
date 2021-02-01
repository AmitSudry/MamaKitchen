package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;

public class Complaint implements Serializable 
{
	private static final long serialVersionUID = -8224097662914849956L;
	
	private String name;
	private String complaint; 
	private boolean isHandled;
	private boolean isRefunded;
		
	public Complaint()
	{
		super();
	}
	
	public Complaint(String name, String complaint) 
	{
		super();
		this.name = name;
		this.complaint = complaint;
		this.isHandled = false;
		this.isRefunded = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
	
	public void Handle(boolean isRefund) {
		this.isHandled = true;
		this.isRefunded = isRefund;
	}
	
	public boolean isHandled() {
		return this.isHandled;
	}
	
	public boolean isRefunded() {
		return this.isRefunded;
	}

	@Override
	public String toString() {
		return "Complaint [name=" + name + ", complaint=" + complaint + ", isHandled=" + String.valueOf(isHandled) + ", isRefunded=" + String.valueOf(isRefunded) + "]";
	}

}
