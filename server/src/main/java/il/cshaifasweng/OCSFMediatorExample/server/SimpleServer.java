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
import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.GetBranches;
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
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		
		return configuration.buildSessionFactory(serviceRegistry);
	}
	
	private static void initializeData() throws Exception 
	{
		//0-regular employee, 1-hostess, 2-dietitian, 3-customer service, 4-branch manager, 5-chain manager
		Employee e1 = new Employee("amit1", "amit123", "Haifa", 0);
		Employee e2 = new Employee("amit2", "amit123456", "Jerusalem", 1);
		Employee e3 = new Employee("amit3", "123456789", "Haifa", 2);
		Employee e4 = new Employee("amit4", "2468", "Haifa", 3);
		Employee e5 = new Employee("amit5", "hello123", "Haifa", 4);
		Employee e6 = new Employee("amit6", "hi2468", "Jerusalem", 5);

		session.save(e1);
		session.save(e2);
		session.save(e3);
		session.save(e4);
		session.save(e5);
		session.save(e6);
		
		session.flush();
		
		Menu menu1 = new Menu("RestMenu_Haifa");
		Branch b1 = new Branch("Haifa");
		
		menu1.setBranch(b1);
		b1.setMenu(menu1);
		
		session.save(menu1);
		session.save(b1);
		
		session.flush();
		
		Item i1 = new Item("Banana", "chain", 50.5, menu1);
		Item i2 = new Item("Apple", "chain", 12, menu1);
		Item i3 = new Item("Yogurt", "branch", 13.5, menu1);
		Item i4 = new Item("Cake", "chain", 105.5, menu1);
		
		Item i5 = new Item("Pie", "chain", 81, menu1);
		Item i6 = new Item("Chicken", "branch", 82, menu1);
		Item i7 = new Item("Rice", "branch", 49, menu1);
		Item i8 = new Item("Hamburger", "branch", 47.5, menu1);
		
		Item i9 = new Item("Pasta", "branch", 35.5, menu1);
		Item i10 = new Item("Pizza", "chain", 25, menu1);
		Item i11 = new Item("Tea", "branch", 10.3, menu1);
		Item i12 = new Item("Coffee", "chain", 8, menu1);
		
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
    	
    	Menu menu2 = new Menu("RestMenu_jerusalem");
		Branch b2 = new Branch("Jerusalem");
		
		menu2.setBranch(b2);
		b2.setMenu(menu2);
		
		session.save(menu2);
		session.save(b2);
		
		session.flush();
		
		Item i13 = new Item("Banana_jerusalem", "chain", 50.5, menu2);
		Item i14 = new Item("Apple_jerusalem", "chain", 12, menu2);
		Item i15 = new Item("Yogurt_jerusalem", "branch", 13.5, menu2);
		Item i16 = new Item("Cake_jerusalem", "chain", 105.5, menu2);
		
		Item i17 = new Item("Pie_jerusalem", "chain", 81, menu2);
		Item i18 = new Item("Chicken_jerusalem", "branch", 82, menu2);
		Item i19 = new Item("Rice_jerusalem", "branch", 49, menu2);
		Item i20 = new Item("Hamburger_jerusalem", "branch", 47.5, menu2);
		
		Item i21 = new Item("Pasta_jerusalem", "branch", 35.5, menu2);
		Item i22 = new Item("Pizza_jerusalem", "chain", 25, menu2);
		Item i23 = new Item("Tea_jerusalem", "branch", 10.3, menu2);
		Item i24 = new Item("Coffee_jerusalem", "chain", 8, menu2);
		
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
	
	private void updateItem(String itemToUpdate, String newPrice, String newType)
	{
		int index = -1;
		{
			for(int i=0; i<items.size(); i++)
			{
				if(items.get(i).getName().equals(itemToUpdate)) //Found target item to update
				{
					index = i;
					break;
				}
			}
		}
		
		if(index != -1)
		{
			items.get(index).setPrice(Double.parseDouble(newPrice));
			items.get(index).setType(newType);
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
			System.out.println("Recived the following reservation event:\n" + msgString);
		}
		else if (msg.getClass().equals(Complaint.class)) //getting the complaint info
		{
			System.out.println("Recived the following reservation event:\n" + msgString);
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
		else if (msgString.startsWith("#updateItem")) //receiving request to update an item
		{
			String[] tokens = msgString.split("\\s+"); //Original expression splitted into tokens
			updateItem(tokens[1], tokens[2], tokens[3]);
		}
		else if(msgString.startsWith("#removeItem")) //receiving request to remove an item
		{
			String[] tokens = msgString.split("\\s+"); //Original expression splitted into tokens
			int index = -1;
			{
				for(int i=0; i<items.size(); i++)
				{
					if(items.get(i).getName().equals(tokens[1])) //Found target item to remove
					{
						index = i;
						break;
					}
				}
			}
			if(index != -1)
				items.remove(index);
		}

	}

}
