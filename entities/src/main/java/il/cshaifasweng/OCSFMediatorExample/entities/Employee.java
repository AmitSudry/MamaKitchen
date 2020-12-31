package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;

@Entity
@Table(name = "employees")
public class Employee implements Serializable 
{
	private static final long serialVersionUID = -8224097662914849956L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@JoinColumn(insertable = false, updatable = false)
	@Column(name = "employee_id")
	private int id;
	
	private String userName;
	private String password;
	private String branchName;
	private int type; //0-regular employee, 1-hostess, 2-dietitian, 3-customer service, 4-branch manager, 5-chain manager
	
	public Employee()
	{
		super();
	}

	public Employee(String userName, String password, String branchName, int type) {
		super();
		this.userName = userName;
		this.password = password;
		this.branchName = branchName;
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", userName=" + userName + ", password=" + password + ", branchName=" + branchName
				+ ", type=" + type + "]";
	}
}
