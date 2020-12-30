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
@Table(name = "menus")
public class Menu implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@JoinColumn(insertable = false, updatable = false)
	@Column(name = "menu_id")
	private int id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
	private List<Item> itemList;
	
	//@OneToOne(targetEntity = il.cshaifasweng.OCSFMediatorExample.entities.Branch.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	//@JoinColumn(name = "branch_id")
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="branch_id")
	private Branch branch;
	
	
	
	public Menu() 
	{
		super();
	}
	
	public Menu(String name) 
	{
		super();
		this.name = name;
		this.itemList = new ArrayList<Item>();
	}

	public Branch getBranch() 
	{
		return branch;
	}

	public void setBranch(Branch branch) 
	{
		this.branch = branch;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public List<Item> getItemList() 
	{
		return itemList;
	}

	public void setItemList(List<Item> itemList) 
	{
		this.itemList = itemList;
	}
	
	public void addItem(Item item) 
	{
		this.itemList.add(item);
	}
	
	public void removeItem(int index) 
	{
		if(index >= 0 && index < itemList.size())
			this.itemList.remove(index);
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", itemList=" + itemList + ", branchName=" + branch.getName() + "]";
	}

	
	
	
	
	
	
}
