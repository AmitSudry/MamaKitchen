package il.cshaifasweng.OCSFMediatorExample.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "deliveries")
public class GetDeliveries implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8224097662914849956L;
	
	List<Delivery> deliveries;
	private String message;
	private LocalTime time;
	
	public GetDeliveries(String message, List<Delivery> deliveries) 
	{
		super();
		this.message = message;
		this.time = LocalTime.now();
		this.deliveries = deliveries;
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
	
	public List<Delivery> getDeliveries() 
	{
		return deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) 
	{
		this.deliveries = deliveries;
	}
}
