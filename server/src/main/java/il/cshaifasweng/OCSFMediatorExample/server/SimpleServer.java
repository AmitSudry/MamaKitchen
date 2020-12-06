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

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Menu;

public class SimpleServer extends AbstractServer 
{
	private static Session session;
	private List<Item> items;
	
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
        	
        	System.out.println("Item list:\n");

        	for (Item item: items) 
        	{
        		System.out.println(item);       		
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
	
	public SimpleServer(int port) 
	{
		super(port);	
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) 
	{
		String msgString = msg.toString();
		System.out.println(msgString);
		List<Item> itemsList = new ArrayList<Item>(this.items);
		
		
		if (msgString.startsWith("#showMenu")) 
		{
			Warning warning = new Warning("Warning from server!", itemsList);
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
		else if (msgString.startsWith("#updateItem"))
		{
			String[] tokens = msgString.split("\\s+"); //Original expression splitted into tokens
			updateItem(tokens[1], tokens[2], tokens[3]);
		}
		else if(msgString.startsWith("#removeItem"))
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
