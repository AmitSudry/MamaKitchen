package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.time.LocalDateTime;
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

import java.io.IOException;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Menu;

public class SimpleServer extends AbstractServer 
{
	private static Session session;
	
	private static SessionFactory getSessionFactory() throws HibernateException 
	{
		Configuration configuration =new Configuration();
		configuration.addAnnotatedClass(Item.class);
		configuration.addAnnotatedClass(Menu.class);
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		
		return configuration.buildSessionFactory(serviceRegistry);
	}
	
	private static void initializeData() throws Exception 
	{
		Menu menu = new Menu("RestMenu");
		
		session.save(menu);
		
		session.flush();
		
		Item i1 = new Item("Banana", "chain", 50.5, menu);
		Item i2 = new Item("Apple", "chain", 12, menu);
		Item i3 = new Item("Yogurt", "branch", 13.5, menu);
		Item i4 = new Item("Cake", "chain", 105.5, menu);
		
		session.save(i1);
		session.save(i2);
		session.save(i3);
		session.save(i4);
		
		session.flush();
		
    	menu.addItem(i1);
    	menu.addItem(i2);
    	menu.addItem(i3);
    	menu.addItem(i4);
    	
    	session.save(menu);
    	
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
	
	public static void setUpDataBase()
	{
		try 
		{ 	
			//Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        	
        	SessionFactory sessionFactory = getSessionFactory();
        	session = sessionFactory.openSession();
        	session.beginTransaction();
        	
        	initializeData();
        	
        	printMenu();
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
	
	public static void printMenu()
	{
		List<Item> items = getAll(Item.class);   	
    	
    	System.out.println("Item list:\n");

    	for (Item item: items) 
    	{
    		System.out.println(item);       		
    	}
    	
    	System.out.println("\nDone!");
	}
	
	
	public SimpleServer(int port) 
	{
		super(port);	
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) 
	{
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) 
		{
			Warning warning = new Warning("Warning from server!");
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
		else if (msgString.startsWith("#showMenu"))
		{
			System.out.println("hi");
		}

	}

}
