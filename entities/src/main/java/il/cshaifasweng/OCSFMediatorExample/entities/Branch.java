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
	
	//@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
	@OneToOne(mappedBy = "branch")
	private Menu menu2;
	
	public Branch() 
	{
		super();
	}
	
	public Branch(String name) 
	{
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getMenu() {
		return menu2;
	}

	public void setMenu(Menu menu) {
		this.menu2 = menu;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", name=" + name + ", menuName=" + menu2.getName() + "]";
	}

	
}
