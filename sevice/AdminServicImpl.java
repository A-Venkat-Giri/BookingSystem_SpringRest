package com.dev.bss.sevice;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.dev.bbs.exceptions.CustomException;
import com.dev.bss.beans.Available;
import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;
import com.dev.bss.dao.DaoAdmin;
import com.dev.bss.dao.DaoAdminImpl;


@Service
public class AdminServicImpl implements ServiceAdmin {
	DaoAdmin admin=new DaoAdminImpl();

	@Override
	public Bus createBus(Bus bus) {
		return admin.createBus(bus);
	}

	@Override
	public Boolean updateBus(Bus bus) {
		return admin.updateBus(bus);
	}

	@Override
	public Bus searchBus(int busId) {
		return admin.searchBus(busId);
	}

	@Override
	public Boolean deletebus(int busId, String password) {
		return admin.deletebus(busId, password);
	}

	@Override
	public List<Bus> busBetween(String source, String destination) {
		return admin.busBetween(source, destination);
	}

	@Override
	public Boolean adminLogin(int adminId, String password) {
		return admin.adminLogin(adminId, password);
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
	public Boolean addAvailability(Available available) {
		return admin.addAvailability(available);
	}

	@Override
	public List<Feedback> showFeedback() {
		return admin.showFeedback();
	}
	
}
