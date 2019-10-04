package com.dev.bss.dao;


import java.util.Date;
import java.util.List;

import com.dev.bss.beans.Booking;
import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;
import com.dev.bss.beans.Ticket;
import com.dev.bss.beans.User;

public interface DaoUser {
	public User createUser(User user);
	public Boolean updateUser(User user,String password);
	public Boolean deleteUser(int userId,String password);
	public Boolean loginUser(int userId,String password);
	public User searchUser(int userId);
	public List<Bus> searchBus(String source,String destination , Date date);
	public List<Booking> getAllTickets(int userId);
	
	
	
	public Booking bookTicket(Ticket ticket);
	public Boolean cancelTicket(int bookingId);
	public Booking getTicket(int bookingId);
	public Integer checkAvailability(int busId,Date date);
	public Boolean giveFeedBack(Feedback feedback);
	public Booking printTicket (int booking_id);
	

}
