
package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.Delivery;
import il.cshaifasweng.OCSFMediatorExample.entities.DiningTable;
import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.GetBranches;
import il.cshaifasweng.OCSFMediatorExample.entities.GetDeliveries;
import il.cshaifasweng.OCSFMediatorExample.entities.GetReports;
import il.cshaifasweng.OCSFMediatorExample.entities.GetReservation;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Login;
import il.cshaifasweng.OCSFMediatorExample.entities.Menu;
import il.cshaifasweng.OCSFMediatorExample.entities.Reservation;
import il.cshaifasweng.OCSFMediatorExample.entities.Branch;

public class SimpleServer extends AbstractServer 
{
	private static Session session;
	private List<Item> items;
	private List<Branch> branches;
	private List<Employee> employees;
	private List<DiningTable> tables;
	
	private List<Delivery> activeDeliveries = new ArrayList<Delivery>();
	private List<Complaint> activeComplaints = new ArrayList<Complaint>();
	private List<Complaint> handledComplaints = new ArrayList<Complaint>();
	private List<Reservation> activeReservations = new ArrayList<Reservation>();
	private static int openHourHaifa, openMinuteHaifa, closeHourHaifa, closeMinuteHaifa;
	private static int openHourJerusalem, openMinuteJerusalem, closeHourJerusalem, closeMinuteJerusalem;
	private static int maxOpen, maxClose;
	private static boolean isDelivery;
	private static boolean priceChangesAllowed;
	
	/*
	 * TODO entities: All types of workers, Branch and chain. 
	 */
	
	private static SessionFactory getSessionFactory() throws HibernateException 
	{
		Configuration configuration =new Configuration();
		configuration.addAnnotatedClass(Item.class);
		configuration.addAnnotatedClass(Menu.class);
		configuration.addAnnotatedClass(Branch.class);
		configuration.addAnnotatedClass(Employee.class);
		configuration.addAnnotatedClass(DiningTable.class);
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		
		return configuration.buildSessionFactory(serviceRegistry);
	}
	
	private static void initializeData() throws Exception 
	{
		priceChangesAllowed = true;
		//0-regular employee, 1-hostess, 2-dietitian, 3-customer service, 4-branch manager, 5-chain manager
		Employee e1 = new Employee("1", "1", "Haifa", 0);
		Employee e2 = new Employee("2", "2", "Jerusalem", 1);
		Employee e3 = new Employee("3", "3", "Haifa", 2);
		Employee e4 = new Employee("4", "4", "Haifa", 3);
		Employee e5 = new Employee("5", "5", "Haifa", 4);
		Employee e6 = new Employee("6", "6", "Jerusalem", 5);
		
		openHourHaifa = 12;
		openMinuteHaifa = 0;
		closeHourHaifa = 8;
		closeMinuteHaifa = 0;
		openHourJerusalem = 12;
		openMinuteJerusalem = 0;
		closeHourJerusalem = 8;
		closeMinuteJerusalem = 0;
		maxOpen = 20;
		maxClose = 10;
		isDelivery = false;
		
		session.save(e1);
		session.save(e2);
		session.save(e3);
		session.save(e4);
		session.save(e5);
		session.save(e6);
		
		session.flush();
		
		Menu menu1 = new Menu("RestMenu_Haifa");
		Branch b1 = new Branch("Haifa", 11, 30, 19, 30, 20, 10, true);
		
		menu1.setBranch(b1);
		b1.setMenu(menu1);
		
		session.save(menu1);
		session.save(b1);
		
		session.flush();
		
		Item i1 = new Item("Banana", "chain", "banana", 50.5, menu1);
		Item i2 = new Item("Apple", "chain", "apple", 12, menu1);
		Item i3 = new Item("Yogurt", "branch", "milk", 13.5, menu1);
		Item i4 = new Item("Cake", "chain", "chocolate, sugar and more", 105.5, menu1);
		
		Item i5 = new Item("Pie", "chain", "ask the seller(changing)", 81, menu1);
		Item i6 = new Item("Chicken", "branch", "chicken, lemon", 82, menu1);
		Item i7 = new Item("Rice", "branch", "Rice, salt", 49, menu1);
		Item i8 = new Item("Hamburger", "branch", "Hamburger, bread", 47.5, menu1);
		
		Item i9 = new Item("Pasta", "branch", "pasta, tomato", 35.5, menu1);
		Item i10 = new Item("Pizza", "chain", "cheese, bread", 25, menu1);
		Item i11 = new Item("Tea", "branch", "tea, sugar", 10.3, menu1);
		Item i12 = new Item("Coffee", "chain", "coffee, sugar", 8, menu1);
		
		session.save(i1);
		session.save(i2);
		session.save(i3);
		session.save(i4);
		session.save(i5);
		session.save(i6);
		session.save(i7);
		session.save(i8);
		session.save(i9);
		session.save(i10);
		session.save(i11);
		session.save(i12);
		
		session.flush();
		
    	menu1.addItem(i1);
    	menu1.addItem(i2);
    	menu1.addItem(i3);
    	menu1.addItem(i4);
    	menu1.addItem(i5);
    	menu1.addItem(i6);
    	menu1.addItem(i7);
    	menu1.addItem(i8);
    	menu1.addItem(i9);
    	menu1.addItem(i10);
    	menu1.addItem(i11);
    	menu1.addItem(i12);
    	
    	session.save(menu1);
    	
    	session.flush();
    	
    	DiningTable t1 = new DiningTable(b1, 1, true);
    	DiningTable t2 = new DiningTable(b1, 2, false);
    	DiningTable t3 = new DiningTable(b1, 4, false);
    	DiningTable t4 = new DiningTable(b1, 7, true);
    	DiningTable t5 = new DiningTable(b1, 10, true);
    	
    	session.save(t1);
    	session.save(t2);
    	session.save(t3);
    	session.save(t4);
    	session.save(t5);
    	
    	session.flush();
    	
    	b1.addTable(t1);
    	b1.addTable(t2);
    	b1.addTable(t3);
    	b1.addTable(t4);
    	b1.addTable(t5);
    	
    	session.save(b1);
    	
    	session.flush();
    	
    	Menu menu2 = new Menu("RestMenu_jerusalem");
		Branch b2 = new Branch("Jerusalem", 8, 30, 20, 30, 20, 10, true);
		
		menu2.setBranch(b2);
		b2.setMenu(menu2);
		
		session.save(menu2);
		session.save(b2);
		
		session.flush();
		
		Item i13 = new Item("Banana_jerusalem", "chain", "banana", 50.5, menu2);
		Item i14 = new Item("Apple_jerusalem", "chain", "apple", 12, menu2);
		Item i15 = new Item("Yogurt_jerusalem", "branch", "milk", 13.5, menu2);
		Item i16 = new Item("Cake_jerusalem", "chain", "chocolate, sugar and more", 105.5, menu2);
		
		Item i17 = new Item("Pie_jerusalem", "chain", "ask the seller(changing)", 81, menu2);
		Item i18 = new Item("Chicken_jerusalem", "branch", "chicken, lemon", 82, menu2);
		Item i19 = new Item("Rice_jerusalem", "branch", "rice, salt", 49, menu2);
		Item i20 = new Item("Hamburger_jerusalem", "branch", "Hamburger, bread", 47.5, menu2);
		
		Item i21 = new Item("Pasta_jerusalem", "branch", "pasta, tomato", 35.5, menu2);
		Item i22 = new Item("Pizza_jerusalem", "chain", "cheese, bread", 25, menu2);
		Item i23 = new Item("Tea_jerusalem", "branch", "tea, sugar", 10.3, menu2);
		Item i24 = new Item("Coffee_jerusalem", "chain", "coffee, sugar", 8, menu2);
		
		session.save(i13);
		session.save(i14);
		session.save(i15);
		session.save(i16);
		session.save(i17);
		session.save(i18);
		session.save(i19);
		session.save(i20);
		session.save(i21);
		session.save(i22);
		session.save(i23);
		session.save(i24);
		
		session.flush();
		
    	menu2.addItem(i13);
    	menu2.addItem(i14);
    	menu2.addItem(i15);
    	menu2.addItem(i16);
    	menu2.addItem(i17);
    	menu2.addItem(i18);
    	menu2.addItem(i19);
    	menu2.addItem(i20);
    	menu2.addItem(i21);
    	menu2.addItem(i22);
    	menu2.addItem(i23);
    	menu2.addItem(i24);

    	
    	session.save(menu2);
    	
    	session.flush();
    	
    	DiningTable t6 = new DiningTable(b2, 1, false);
    	DiningTable t7 = new DiningTable(b2, 3, true);
    	DiningTable t8 = new DiningTable(b2, 6, true);
    	DiningTable t9 = new DiningTable(b2, 8, false);
    	DiningTable t10 = new DiningTable(b2, 15, false);
    	
    	session.save(t6);
    	session.save(t7);
    	session.save(t8);
    	session.save(t9);
    	session.save(t10);
    	
    	session.flush();
    	
    	b2.addTable(t6);
    	b2.addTable(t7);
    	b2.addTable(t8);
    	b2.addTable(t9);
    	b2.addTable(t10);
    	
    	session.save(b2);
    	
    	session.flush();
    	
    	session.getTransaction().commit();
	}
	
	public static <T> List<T> getAll(Class<T> object) 
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}
	
	public void setUpDataBase()
	{
		try 
		{ 	
			//Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        	
        	SessionFactory sessionFactory = getSessionFactory();
        	session = sessionFactory.openSession();
        	session.beginTransaction();
        	
        	initializeData();
        	
        	this.items = getAll(Item.class);   	
        	this.branches = getAll(Branch.class);
        	this.employees = getAll(Employee.class);
        	this.tables = getAll(DiningTable.class);
        	
        	System.out.println("\nItem list:\n");

        	for (Item item: items) 
        	{
        		System.out.println(item);       		
        	}
        	
        	System.out.println("\nBranch list:\n");

        	for (Branch branch: branches) 
        	{
        		System.out.println(branch);       		
        	}
        	
        	System.out.println("\nTables list:\n");

        	for (DiningTable table: tables) 
        	{
        		System.out.println(table);       		
        	}
        	
        	System.out.println("\nEmployees list:\n");

        	for (Employee employee: employees) 
        	{
        		System.out.println(employee);       		
        	}
        	
        	System.out.println("\nDone!");
		} 
    	catch (Exception e) 
        {
			if (session != null) 
			{
				session.getTransaction().rollback();
			}
			
			e.printStackTrace();
		} 
        finally 
        {
			if (session != null) 
			{
				session.close();
				session.getSessionFactory().close();
			}
		}
	}
	
	private void updateItem(String itemToUpdate, String newPrice, String newType, String branchName, String newIngridients)
	{
		int target = -1;
		
		for (int i=0; i<branches.size(); i++) 
    	{
    		if(branches.get(i).getName().equals(branchName))
    		{
    			target = i;
    			break;
    		}
    	}
		
		if(target == -1)
		{
			System.out.println("Invalid branch name in update item");
			return;
		}
			
		
		int index = -1;
		for(int i=0; i<branches.get(target).getMenu().getItemList().size(); i++)
		{
			if(branches.get(target).getMenu().getItemList().get(i).getName().equals(itemToUpdate)) //Found target item to update
			{
				index = i;
				break;
			}
		}

		
		if(index != -1)
		{
			//System.out.println(branches.get(target).getMenu().getItemList().get(index).getName() + " " + branches.get(target).getName());
			if(!newPrice.contains("miss") && priceChangesAllowed) {
				branches.get(target).getMenu().getItemList().get(index).setPrice(Double.parseDouble(newPrice));
			}
			branches.get(target).getMenu().getItemList().get(index).setType(newType);
			branches.get(target).getMenu().getItemList().get(index).setIngridients(newIngridients);
		}	
	}
	
	private void addItem(Menu menu, Item item) {
		menu.getItemList().add(item);
		menu.getItemList().get(menu.getItemList().size() - 1).setId(menu.getItemList().size());
		session.save(item);
		session.save(menu);
		session.flush();
		session.getTransaction().commit();
	}
	
	private void removeItem(String itemToRemove, String branchName)
	{
		int target = -1;
		
		for (int i=0; i<branches.size(); i++) 
    	{
    		if(branches.get(i).getName().equals(branchName))
    		{
    			target = i;
    			break;
    		}
    	}
		
		if(target == -1)
		{
			System.out.println("Invalid branch name in update item");
			return;
		}
			
		int index = -1;
		for(int i=0; i<branches.get(target).getMenu().getItemList().size(); i++)
		{
			if(branches.get(target).getMenu().getItemList().get(i).getName().equals(itemToRemove)) //Found target item to update
			{
				index = i;
				break;
			}
		}
		
		if(index != -1)
		{
			//System.out.println(branches.get(target).getMenu().getItemList().get(index).getName() + " " + branches.get(target).getName());
			branches.get(target).getMenu().getItemList().remove(index);
		}	
	}
	
	private Employee isEmployee(String username, String password)
	{
		for (Employee employee: employees) 
    	{
    		if(employee.getUserName().equals(username) && employee.getPassword().equals(password))
    			return new Employee(employee.getUserName(),employee.getPassword(),
    					employee.getBranchName(), employee.getType());
    	}
		
		return null;
	}
	
	public SimpleServer(int port) 
	{
		super(port);	
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) 
	{
		String msgString = msg.toString();
		//System.out.println(msgString);
		List<Branch> branchesList = new ArrayList<Branch>(this.branches);
		
		System.out.println();
		
		if (msg.getClass().equals(Delivery.class)) //getting the delivery info
		{
			activeDeliveries.add((Delivery) msg);
			System.out.println("Recived the following delivery event:\n" + msgString);
		}
		else if (msg.getClass().equals(Login.class))  //getting the login info
		{
			System.out.println("Recived the following login event:\n" + msgString);

			Login l = (Login)msg;
			Employee e = isEmployee(l.getUsername(), l.getPassword());
			try 
			{
				if(e != null)
				{
					client.sendToClient(e);
				}
				else
				{
					client.sendToClient(new Employee("", "", "", -1));

				}
			} 
			catch (IOException exc) 
			{
				exc.printStackTrace();
			}			
		}
		else if (msg.getClass().equals(Reservation.class)) //getting the reservation info
		{
			activeReservations.add((Reservation) msg);
			System.out.println("Recived the following reservation event:\n" + msgString);
			
			int target = -1;
			
			for (int i=0; i<branches.size(); i++) 
	    	{
	    		if(branches.get(i).getName().equals(((Reservation) msg).getBranch()))
	    		{
	    			target = i;
	    			break;
	    		}
	    	}
			
			branches.get(target).getTable(((Reservation) msg).getTableId()).addReservation(((Reservation) msg));
			
		}
		else if (msg.getClass().equals(Complaint.class)) //getting the complaint info
		{
			if (!((Complaint) msg).isHandled()) activeComplaints.add((Complaint) msg);
			else {
				handledComplaints.add((Complaint) msg);
				
				for (int i = 0; i < activeComplaints.size(); i++) {
					if (activeComplaints.get(i).getName().equals(((Complaint) msg).getName()) && activeComplaints.get(i).getComplaint().equals(((Complaint) msg).getComplaint())) {
						activeComplaints.remove(i);
					}
				}
			}
			System.out.println("Recived the following complaint event:\n" + msgString);
		}
		else if (msgString.startsWith("#reports"))
		{
			System.out.println("im here!");
			List<Complaint> complaints = new ArrayList<Complaint>(this.activeComplaints);
			for(int i=0; i<complaints.size(); i++)
			{
				System.out.println(complaints.get(i).toString());
			}
			GetReports t = new GetReports("Hello there!", complaints);
			try 
			{
				client.sendToClient(t);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else if (msgString.startsWith("#showMenu")) //receiving request to forward the branch list
		{
			GetBranches warning = new GetBranches("Warning from server!", branchesList);
			try 
			{
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else if (msgString.startsWith("#getActiveDeliveries")) //receiving request to forward the branch list
		{
			GetDeliveries warning = new GetDeliveries("Warning from server!", activeDeliveries);
			try 
			{
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else if (msgString.startsWith("#getActiveReservations")) //receiving request to forward the branch list
		{
			GetReservation warning = new GetReservation("Warning from server!", activeReservations);
			try 
			{
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		else if (msgString.startsWith("#removeDelivery"))
		{
			String[] tokens = msgString.split("\\s+");
			int index = Integer.parseInt(tokens[1]);
			activeDeliveries.remove(index);
		}
		else if (msgString.startsWith("#removeReservation"))
		{
			String[] tokens = msgString.split("\\s+");
			int index = Integer.parseInt(tokens[1]);
			activeReservations.remove(index);
		}
		else if (msgString.startsWith("#updateItem")) //receiving requests to update an item
		{
			String[] tokens = msgString.split("\\s+"); //Original expression splitted into tokens
			/*
			for(int i=0; i<tokens.length; i++)
			{
				System.out.println("token " +i+" is:" + tokens[i]);
			}
			*/
			updateItem(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
		}
		else if (msgString.startsWith("#addItem")) //receiving requests to update an item
		{
			String[] tokens = msgString.split("\\s+"); //Original expression splitted into tokens
			/*
			for(int i=0; i<tokens.length; i++)
			{
				System.out.println("token " +i+" is:" + tokens[i]);
			}
			*/
			if(tokens[5].contains("Jerusalem"))
				tokens[5] = "1";
			else
				tokens[5] = "0";
			Menu menu = branchesList.get(Integer.parseInt(tokens[5])).getMenu();
			
			//Item item = new Item(tokens[1], tokens[2], Float.parseFloat(tokens[3]), menu);
			Item item = new Item(tokens[1], tokens[2], tokens[3], Float.parseFloat(tokens[4]), menu);
			
			addItem(menu, item);
						
		}
		else if(msgString.startsWith("#removeItem")) //receiving request to remove an item
		{
			String[] tokens = msgString.split("\\s+"); //Original expression splitted into tokens
			/*
			for(int i=0; i<tokens.length; i++)
			{
				System.out.println("token " +i+" is:" + tokens[i]);
			}
			*/
			removeItem(tokens[1], tokens[2]);
		}
		else if(msgString.contains("#setCurrentRegulations")) {
			String[] tokens = msgString.split("\\s+");
			int openHourHaifa = Integer.parseInt(tokens[1]);
			int openMinuteHaifa = Integer.parseInt(tokens[2]);
			int closeHourHaifa = Integer.parseInt(tokens[3]);
			int closeMinuteHaifa = Integer.parseInt(tokens[4]);
			int openHourJerusalem = Integer.parseInt(tokens[5]);
			int openMinuteJerusalem = Integer.parseInt(tokens[6]);
			int closeHourJerusalem = Integer.parseInt(tokens[7]);
			int closeMinuteJerusalem = Integer.parseInt(tokens[8]);
			int maxOpen = Integer.parseInt(tokens[9]);
			int maxClose = Integer.parseInt(tokens[10]);
			boolean isDelivery = Boolean.parseBoolean(tokens[11]);
			branches.get(0).setOpenHour(openHourHaifa);
			branches.get(0).setOpenMinute(openMinuteHaifa);
			branches.get(0).setCloseHour(closeHourHaifa);
			branches.get(0).setCloseMinute(closeMinuteHaifa);
			branches.get(0).setMaxOpen(maxOpen);
			branches.get(0).setMaxClose(maxClose);
			branches.get(0).setIsDelivery(isDelivery);
			branches.get(1).setOpenHour(openHourJerusalem);
			branches.get(1).setOpenMinute(openMinuteJerusalem);
			branches.get(1).setCloseHour(closeHourJerusalem);
			branches.get(1).setCloseMinute(closeMinuteJerusalem);
			branches.get(1).setMaxOpen(maxOpen);
			branches.get(1).setMaxClose(maxClose);
			branches.get(1).setIsDelivery(isDelivery);
		}
		else if(msgString.contains("#changePriceChangesPolicy")) {
			priceChangesAllowed = !priceChangesAllowed;
		}
	}
}