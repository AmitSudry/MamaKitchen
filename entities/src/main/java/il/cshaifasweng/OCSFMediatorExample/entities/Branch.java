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
import java.util.*;

@Entity
@Table(name = "branchs")
public class Branch implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@JoinColumn(insertable = false, updatable = false)
	@Column(name = "branch_id")
	private int id;
	
	private String name;
	
	private int openingHour; 
	private int closingHour; 
	private int openHour, openMinute, closeHour, closeMinute;
	private int maxOpen, maxClose;
	private boolean isDelivery;
	
	
	private int deliveryCounter;
	private int takeawayCounter;
	private int reservationCounter;
	private int rejectedCustomersCounter;
	private int complaintsToal;
	private int complaintsClosed;
	
	//@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
	@OneToOne(mappedBy = "branch")
	private Menu menu;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
	private List<DiningTable> tableList;
	
	public Branch() 
	{
		super();
	}
	
	public Branch(String name, int openHour, int openMinute, int closeHour, int closeMinute, int maxOpen, int maxClose, boolean isDelivery) 
	{
		super();
		this.name = name;
		this.openHour = openHour;
		this.closeHour = closeHour;
		this.openMinute = openMinute;
		this.closeMinute = closeMinute;
		this.maxOpen = maxOpen;
		this.maxClose = maxClose;
		this.isDelivery = isDelivery;
		
		this.tableList = new ArrayList<DiningTable>();
		
		deliveryCounter = 0;
		takeawayCounter = 0;
		reservationCounter = 0;
		rejectedCustomersCounter = 0;
		complaintsToal = 0;
		complaintsClosed = 0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public void addTable(DiningTable table) {
		this.tableList.add(table);
	}
	
	public List<DiningTable> getTableList() {
		return tableList;
	}
	
	public DiningTable getTable(int id) {
		for (DiningTable table: tableList) {
			if (table.getId() == id) return table;
		}
		return null;
	}

	public int getOpenHour() {
		return openHour;
	}

	public void setOpenHour(int openHour) {
		this.openHour = openHour;
	}

	public int getCloseHour() {
		return closeHour;
	}
	
	public void setCloseHour(int closeHour) {
		this.closeHour = closeHour;
	}
	
	public void setCloseMinute(int closeMinute) {
		this.closeMinute = closeMinute;
	}
	
	public int getCloseMinute() {
		return closeMinute;
	}
	
	public int getOpenMinute() {
		return openMinute;
	}
	
	public void setOpenMinute(int openMinute) {
		this.openMinute = openMinute;
	}
	
	public int getMaxOpen() {
		return maxOpen;
	}
	
	public void setMaxOpen(int maxOpen) {
		this.maxOpen = maxOpen;
	}
	
	public int getMaxClose() {
		return maxClose;
	}
	
	public void setMaxClose(int maxClose) {
		this.maxClose = maxClose;
	}
	
	public boolean getIsDelivery() {
		return isDelivery;
	}
	
	public void setIsDelivery(boolean isDelivery) {
		this.isDelivery = isDelivery;
	}
	
	public int getDeliveryCounter() {
		return deliveryCounter;
	}

	public void incDeliveryCounter() {
		this.deliveryCounter++;
	}

	public int getTakeawayCounter() {
		return takeawayCounter;
	}

	public void incTakeawayCounter() {
		this.takeawayCounter++;
	}

	public int getRejectedCustomersCounter() {
		return rejectedCustomersCounter;
	}

	public void incRejectedCustomersCounter() {
		this.rejectedCustomersCounter++;
	}

	public int getComplaintsToal() {
		return complaintsToal;
	}

	public void incComplaintsToal() {
		this.complaintsToal++;
	}

	public int getComplaintsClosed() {
		return complaintsClosed;
	}

	public void incComplaintsClosed() {
		this.complaintsClosed++;
	}
	
	public int getReservationCounter() {
		return reservationCounter;
	}
	
	public void incReservationCounter() {
		this.reservationCounter++;
	}


	@Override
	public String toString() {
		return "Branch [id=" + id + ", name=" + name + ", openingHour=" + openingHour + ", closingHour=" + closingHour
				+ ", menuName=" + menu.getName() + "]";
	}
}
