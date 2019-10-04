package com.dev.bbs.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.bss.beans.Admin;
import com.dev.bss.beans.Available;
import com.dev.bss.beans.Booking;
import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;
import com.dev.bss.beans.Ticket;
import com.dev.bss.beans.User;
import com.dev.bss.sevice.ServiceUser;

@RestController
@RequestMapping(path="/user")
@CrossOrigin(origins="*",allowedHeaders="*")
public class UserController {
	
	@Autowired
	ServiceUser services;
	
	
	@PostMapping(path = "/create",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User createUser(@RequestBody User user)
	{
		 return user = services.createUser(user);
	}
	
	@GetMapping(path = "/get/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@PathVariable("id") int userId)
	{
		User user = services.searchUser(userId);
		return user;
	}
	@PostMapping(value = "/updateuser",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@RequestBody User user)
	{
		Boolean state = services.updateUser(user, user.getPassword());
		if(state)
		{
		 user = services.searchUser(user.getUserId());
		 return user;
		}
		return null;
		
	}
	//modified need to check
	@DeleteMapping(path = "/delete/{userId}/{password}",produces = MediaType.APPLICATION_JSON_VALUE)
	public User deleteUser(@PathVariable("userId") int userId,@PathVariable("password") String password) {
		User user = services.searchUser(userId);
		Boolean state = services.deleteUser(userId, password);
		return user;
	}
	
	@PostMapping(path = "/login",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User loginUser(@RequestBody User user) {
		Boolean check = services.loginUser(user.getUserId(), user.getPassword());
		if(check) {
			return user;
		}
		return null;
	}

	//need to check
	@GetMapping(path ="/search/bus/{source}/{destination}/{date}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Bus> searchBus(@PathVariable("source") String source,@PathVariable("destination") String destination,@PathVariable("date") Date date)
	{
		List<Bus> list = services.searchBus(source, destination, date);
		if(list!=null) {
			return list;
		}
		return null;
	}
	
	
	@PostMapping(path = "/book/ticket", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Booking bookTicket(@RequestBody Ticket ticket) {
		Booking check = services.bookTicket(ticket);
		if(check!=null) {
			return check;
		}
		return null;
	}
	
	//detached entity
	@PostMapping(path = "/give-feedback", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Feedback giveFeedback(@RequestBody Feedback feedback) {
		Boolean list = services.giveFeedBack(feedback);
		if(list!=null) {
			return feedback;
		}
		return null;
	}
	
	
	@DeleteMapping(path = "/cancel/ticket/{id}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean cancelTicket(@PathVariable ("id") int r) {
		
		Boolean ticket = services.cancelTicket(r);
		if(ticket)
		{
			return true;
		}
		return false;
		
	}
	
	
	@GetMapping(path = "/get/ticket/{booking_id}",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Booking getTicket(@PathVariable("booking_id") int booking_id)
	{
		Booking check = services.getTicket(booking_id);
		if(check!=null) {
			return check;
		}
		return null;
	}
	
	@GetMapping(path = "/get/all-tickets/{userId}",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Booking> getAllTickets(@PathVariable("userId") int userId)
	{
		List<Booking> tickets = services.getAllTickets(userId);
		if(tickets!=null) {
			return tickets;
		}
		return null;
	}
	
	//need to check
	@GetMapping(path = "/check-availability/{bus_id}/{journeyDate}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer checkAvailability(@PathVariable("bus_id") int bus_id,@PathVariable("journeyDate") Date journeyDate)
	{
		Integer seats = services.checkAvailability(bus_id, journeyDate);
		if(seats!=null) {
			return seats;
		}
		return null;
	}
	
	@GetMapping(path = "/printticket/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Booking printTicket(@PathVariable("id") int userId)
	{
		Booking book = services.printTicket(userId);
		return book;
	}
	
	
}
