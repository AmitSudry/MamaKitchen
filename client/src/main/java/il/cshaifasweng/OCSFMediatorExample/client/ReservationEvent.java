package il.cshaifasweng.OCSFMediatorExample.client;

import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Reservation;

public class ReservationEvent 
{
	private Reservation reservationDetails;
	
	public Reservation getReservation() 
	{
		return reservationDetails;
	}

	public ReservationEvent(Reservation resInfo) 
	{
		this.reservationDetails = resInfo;
	}
}
