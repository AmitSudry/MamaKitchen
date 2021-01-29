package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;

@Entity
@Table(name = "dining_tables")
public class DiningTable implements Serializable  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "table_id")
	private int id;
	
	private int num_of_people;
	private boolean isInside;
	
	@ElementCollection
	private List<Reservation> reservations;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id")
	private Branch branch;
	
	public DiningTable()
	{
		super();
	}
	
	public DiningTable(Branch branch, int num_of_people, boolean isInside) {
		super();
		this.branch = branch;
		this.num_of_people = num_of_people;
		this.isInside = isInside;
		this.reservations = new ArrayList<Reservation>();
	}
	
	public int reserveTable(Reservation r) {
		if (r.getNumOfPeople() > num_of_people || r.isInsideDining() != isInside) return -1;
		
		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).getDate().equals(r.getDate()) && reservations.get(i).getHour() == r.getHour()) return -1;
		}
		
		reservations.add(r);
		
		for (Reservation l: reservations) {
			System.out.println(l);
		}
		return id;
	}
	
	@Override
	public String toString() {
		return "Table [id=" + id + ", branchName=" + branch.getName() + ", numOfPeople=" + num_of_people + ", isInside=" + String.valueOf(isInside) + "]";
	}
	
	public int getId() {
		return id;
	}
	
	public void addReservation(Reservation r) {
		reservations.add(r);
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
		
}
