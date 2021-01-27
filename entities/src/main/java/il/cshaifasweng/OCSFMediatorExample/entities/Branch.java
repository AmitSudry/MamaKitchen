package il.cshaifasweng.OCSFMediatorExample.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	//@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
	@OneToOne(mappedBy = "branch")
	private Menu menu;
	
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

	@Override
	public String toString() {
		return "Branch [id=" + id + ", name=" + name + ", openingHour=" + openingHour + ", closingHour=" + closingHour
				+ ", menuName=" + menu.getName() + "]";
	}
}
