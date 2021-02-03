package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;

public class Reservation implements Serializable 
{
	private static final long serialVersionUID = -8224097662914849956L;
	
	private String branch;
	private String date; //date of delivey "22.11.20"
	private int numOfPeople; //hour of deliver "1355" may also be 850 which means 08:50!!!!!
	private int hour; 
	private String name; //name of customer
	private boolean isInsideDining; //address in case of not TA
	private String phoneNumber; 
	private int table_id;
	
	public Reservation()
	{
		super();
	}

	public Reservation(String branch, String date, int numOfPeople, int hour, String name, boolean isInsideDining,
			String phoneNumber, int table_id) {
		super();
		this.branch = branch;
		this.date = date;
		this.numOfPeople = numOfPeople;
		this.hour = hour;
		this.name = name;
		this.isInsideDining = isInsideDining;
		this.phoneNumber = phoneNumber;
		this.table_id = table_id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNumOfPeople() {
		return numOfPeople;
	}

	public void setNumOfPeople(int numOfPeople) {
		this.numOfPeople = numOfPeople;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInsideDining() {
		return isInsideDining;
	}

	public void setInsideDining(boolean isInsideDining) {
		this.isInsideDining = isInsideDining;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setTableId(int id) {
		this.table_id = id;
	}
	
	public int getTableId() {
		return this.table_id;
	}

	@Override
	public String toString() {
		return "Reservation [branch=" + branch + ", date=" + date + ", numOfPeople=" + numOfPeople + ", hour=" + hour
				+ ", name=" + name + ", isInsideDining=" + isInsideDining + ", phoneNumber=" + phoneNumber + "]";
	}
	
	
}
