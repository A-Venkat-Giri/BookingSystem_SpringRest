package com.dev.bbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.bss.beans.Admin;
import com.dev.bss.beans.Available;
import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;
import com.dev.bss.sevice.ServiceAdmin;

@RestController
@RequestMapping(path="/admin")
@CrossOrigin(origins="*",allowedHeaders="*")
public class AdminController {
	@Autowired
	ServiceAdmin services;
	
	
	@PostMapping(path = "/rest/bus/create",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Bus createBus(@RequestBody Bus bus) {
		Bus check = services.createBus(bus);
		
			return check;
		
		
	}
	
	
	@DeleteMapping(path = "/rest/bus/delete/{busId}/{password}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Bus deleteBus(@PathVariable("busId") int id,@PathVariable("password") String  password) {
		 Bus bus = services.searchBus(id);
		 Boolean check = services.deletebus(id, password);
		 if(check) {
		
				return bus;
			}
			return null;
	}
	
	
	@GetMapping(path = "/rest/bus/{id}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Bus getBus(@PathVariable("id") int id) {
		
		Bus bus = services.searchBus(id);
		if(bus != null) {
			return bus;
		}
		return null;
	}
	
	@PutMapping(path = "/rest/bus/update",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Bus updateBus(@RequestBody Bus bus) {
		Boolean check = services.updateBus(bus);
		if(check) {
			return bus;
		}
		return null;
	}
	
	//check
	@PostMapping(path = "/rest/admin/login",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Admin loginAdmin(@RequestBody Admin admin) {
		Boolean check = services.adminLogin(admin.getAdminId(),admin.getPassword());
		if(check) {
			return admin;
		}
		return null;
	}
	
	@GetMapping(path = "/rest/admin/{source}/{destination}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Bus> getBusBetween(@PathVariable ("source") String source,@PathVariable ("destination") String destination) {
		List<Bus> list = services.busBetween(source,destination);
		if(list!=null) {
			return list;
		}
		return null;
	}
	
	@GetMapping(path = "/rest/admin/show-feedback", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Feedback> showFeedback() {
		List<Feedback> list = services.showFeedback();
		if(list!=null) {
			return list;
		}
		return null;
	}
//	check
	@PutMapping(path = "/rest/bus/add-availalibity", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Available addAvailability(@RequestBody Available available) {
		Boolean check = services.addAvailability(available);
		if(check) {
			return available;
		}
		return null;
	}
	
	
}