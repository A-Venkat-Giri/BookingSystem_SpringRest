package com.dev.bss.sevice;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.dev.bbs.exceptions.CustomException;
import com.dev.bss.beans.Booking;
import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;
import com.dev.bss.beans.Ticket;
import com.dev.bss.beans.User;
import com.dev.bss.dao.DaoUser;
import com.dev.bss.dao.DaoUserImpl;

@Service
public class UserServiceImpl implements ServiceUser {
	DaoUser dao=new DaoUserImpl();

	@Override
	public User createUser(User user) {
		return dao.createUser(user);
	}

	@Override
	public Boolean updateUser(User user, String password ) {
		return dao.updateUser(user, password);
	}

	@Override
	public Boolean deleteUser(int user_id, String password) {
		return dao.deleteUser(user_id, password);
	}

	@Override
	public Boolean loginUser(int user_id, String password) {
		return dao.loginUser(user_id, password);
	}

	@Override
	public User searchUser(int user_id) {
		return dao.searchUser(user_id);
	}

	@Override
	public Booking bookTicket(Ticket ticket) {
		return dao.bookTicket(ticket);
	}

	@Override
	public Boolean cancelTicket(int booking_id) {
		return dao.cancelTicket(booking_id);
	}

	@Override
	public Booking getTicket(int booking_id) {
		return dao.getTicket(booking_id);
	}

	@Override
	public Integer checkAvailability(int bus_id, Date date) {
		return dao.checkAvailability(bus_id, date);
	}

	@Override
	public List<Bus> searchBus(String source, String destination, Date date) {
		return dao.searchBus(source, destination, date);
	}

	@Override
	public String checkUserIdAndBookinIdAndBusId(String number) {
		Pattern pat = Pattern.compile("\\d+");       // represents any number
		Matcher mat = pat.matcher(number);
		 if(mat.matches())
		 {
			 return number;
		 }
		 else
		 {
			 throw new CustomException("EnterIntegerException:EnterValidInteger");
		 }
	}

	@Override
	public String checkContact(String contact) {
      Pattern pat = Pattern.compile("\\d{10,10}");
      Matcher mat =  pat.matcher(contact);
      if(mat.matches())
      {
    	  return contact;
      }
      else
      {
    	  throw new CustomException("ContactInputException:ProvideProperContact");
      }
   
	}

	@Override
	public String checkEmail(String email)
	{
		Pattern pattern=Pattern.compile("\\w+@\\w+.\\w+");
		Matcher matcher=pattern.matcher(email);
		if(matcher.matches())
		{
			return email;
		}
		else
		{
			throw new CustomException("EmailException:ProvideProperEmail");
		}
	}

	@Override
	public Boolean giveFeedBack(Feedback feedback) {
		return dao.giveFeedBack(feedback);
	}

	@Override
	public List<Booking> getAllTickets(int userId) {
		return dao.getAllTickets(userId);
	}

	@Override
	public Booking printTicket(int booking_id) {
		
		return dao.printTicket(booking_id);
	}
}
