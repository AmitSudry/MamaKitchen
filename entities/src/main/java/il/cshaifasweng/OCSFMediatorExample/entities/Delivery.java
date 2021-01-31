package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;

public class Delivery implements Serializable 
{
	private static final long serialVersionUID = -8224097662914849956L;
	
	List<Item> itemsList;
	private String date; //date of delivey "22.11.20"
	private int hour; //hour of deliver "1355" may also be 850 which means 08:50!!!!!
	private boolean isTA; 
	private String name; //name of customer
	private String address; //address in case of not TA
	private String phoneNumber; 
	private double total; //total price of delivery(if not TA it includes the dlivery fee)
	private boolean isCreditCard; //payment method(cas/credit)
	private int id;
	
	public Delivery()
	{
		super();
	}
	public Delivery(List<Item> itemsList, String date, int hour, boolean isTA, String name, String address,
			String phoneNumber, double total, boolean isCreditCard) 
	{
		super();
		this.itemsList = itemsList;
		this.date = date;
		this.hour = hour;
		this.isTA = isTA;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.total = total;
		this.isCreditCard = isCreditCard;
	}
	
	public List<Item> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<Item> itemsList) {
		this.itemsList = itemsList;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public boolean isTA() {
		return isTA;
	}

	public void setTA(boolean isTA) {
		this.isTA = isTA;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public boolean isCreditCard() {
		return isCreditCard;
	}

	public void setCreditCard(boolean isCreditCard) {
		this.isCreditCard = isCreditCard;
	}

	@Override
	public String toString() {
		return "Delivery [itemsList=" + itemsList + "\n" + ", date=" + date + ", hour=" + hour + ", isTA=" + isTA + ", name="
				+ name + ", address=" + address + ", phoneNumber=" + phoneNumber + ", total=" + total
				+ ", isCreditCard=" + isCreditCard + "]";
	}

}
