package com.dev.bss.dao;

import java.util.List;

import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;

public interface DaoAdmin {
	public Bus createBus(Bus bus);
	public Boolean updateBus(Bus bus);
	public Bus searchBus(int busId);
	public Boolean deletebus(int busId,String password);
	public List<Bus> busBetween(String source,String destination);
	public List<Feedback> showFeedback();
	
	public Boolean addAvailability(com.dev.bss.beans.Available available);
	public Boolean adminLogin(int adminId, String password);
}
