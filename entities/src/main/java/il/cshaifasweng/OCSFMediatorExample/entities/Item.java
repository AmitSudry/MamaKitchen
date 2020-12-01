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
import javax.persistence.OrderBy;
import javax.persistence.Table;


import java.util.*;

@Entity
@Table(name = "itmes")
public class Item 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private int id;

	@Column(name = "item_name")
	private String name;

	//Either be chain item or branch item
	@Column(name = "item_type")
	private String type;
	
	@Column(name = "item_price")
	private double price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;
	
	public Item() 
	{
		super();
	}
	
	public Item(String name, String type, double price, Menu menu) 
	{
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.menu = menu;
	}
	
	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public double getPrice() 
	{
		return price;
	}

	public void setPrice(double price) 
	{
		this.price = price;
	}

	public Menu getMenu() 
	{
		return menu;
	}

	public void setMenu(Menu menu) 
	{
		this.menu = menu;
	}

	@Override
	public String toString() 
	{
		return "Item [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + "]";
	}
	
	
}
