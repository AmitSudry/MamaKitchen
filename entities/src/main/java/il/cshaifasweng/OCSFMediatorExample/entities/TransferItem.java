package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class TransferItem implements Serializable 
{
	private static final long serialVersionUID = -8224097662914849956L;
	
	private boolean remove = false;
	private String itemNameToUpdate = "";
	private String newType = "";
	private double newPrice = 0;
	
	public TransferItem(boolean remove, String newType, double newPrice, String itemName) 
	{
		super();
		this.remove = remove;
		this.newType = newType;

		this.itemNameToUpdate = itemName;
	}
	
	public String getItemNameToUpdate() 
	{
		return itemNameToUpdate;
	}

	public void setItemNameToUpdate(String itemNameToUpdate) 
	{
		this.itemNameToUpdate = itemNameToUpdate;
	}

	public boolean isRemove() 
	{
		return remove;
	}
	
	public void setRemove(boolean remove) 
	{
		this.remove = remove;
	}
	
	public String getNewType() 
	{
		return newType;
	}
	
	public void setNewType(String newType) 
	{
		this.newType = newType;
	}
	
	public double getNewPrice() 
	{
		return newPrice;
	}
	
	public void setNewPrice(double newPrice) 
	{
		this.newPrice = newPrice;
	}
}
