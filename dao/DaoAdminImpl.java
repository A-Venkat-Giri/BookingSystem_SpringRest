package com.dev.bss.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.dev.bbs.exceptions.CustomException;
import com.dev.bbs.exceptions.DeleteFailedException;
import com.dev.bbs.exceptions.UpdateFailedException;
import com.dev.bss.beans.Admin;
import com.dev.bss.beans.Available;
import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;

@Repository
public class DaoAdminImpl implements DaoAdmin {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQLUnit");
	@Override
	public Bus createBus(Bus bus) {
		try {
			EntityManager manager=emf.createEntityManager();
			manager.getTransaction().begin();
				manager.persist(bus);
				manager.getTransaction().commit();
				return bus;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Boolean updateBus(Bus bus) {
		try {
			EntityManager em = emf.createEntityManager();

			em.getTransaction().begin();
			Bus bus1 = em.find(Bus.class, bus.getBusId());
			bus1.setBusName(bus.getBusName());
			bus1.setBusType(bus.getBusType());
			bus1.setDestination(bus.getDestination());
			bus1.setSource(bus.getSource());
			bus1.setPrice(bus.getPrice());
			bus1.setTotalSeats(bus.getTotalSeats());
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			throw new UpdateFailedException("FailedTOUpdate");
		}
	}


	@Override
	public Bus searchBus(int busId) {
		 try {
			EntityManager em = emf.createEntityManager();
			   Query query = em.createQuery("from Bus u where u.busId= :busid ");
			   query.setParameter("busid", busId );
			   em.getTransaction().begin();
			   List<Bus> bus=query.getResultList();
			   em.getTransaction().commit();
			  Bus bus1= bus.get(0);
			  return bus1;
		} catch (Exception e) {
			throw new CustomException("SearchBusException");
		}
	}

	@Override
	public Boolean deletebus(int busId, String password) {
		try {
			EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				Bus bus = em.find(Bus.class,busId );
				Admin admin =em.find(Admin.class, 66);
				if(admin.getPassword().equals(password))
				{
					em.remove(bus);
					em.getTransaction().commit();
					return true;
				}
				else
				{
					throw new DeleteFailedException("password wrong ");
				}
				
		} catch (Exception e) {
			throw new DeleteFailedException("DeleteFailed");
		}
		
	}

	@Override
	public List<Bus> busBetween(String source, String destination) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("From Bus b where b.source = :source and b.destination = :destination ");
			query.setParameter("source", source);
			query.setParameter("destination", destination);
			em.getTransaction().begin();
			List<Bus> buses = query.getResultList();
			em.getTransaction().commit();
			return buses;
		} catch (Exception e) {
			throw new CustomException("SearchBusException");
		}
	}

	@Override
	public Boolean adminLogin(int adminId, String password) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("from Admin a where a.adminId= :adminid and a.password= :password ");
			query.setParameter("adminid", adminId);
			query.setParameter("password", password);
			em.getTransaction().begin();
			List<Admin> admin = query.getResultList();
			em.getTransaction().commit();
			Admin admin1 =  admin.get(0);
			return true;
		} catch (Exception e) {
			throw new com.dev.bbs.exceptions.LoginException("LoginFAILED");
		}
	}

	@Override
	public Boolean addAvailability(Available available) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		
			em.persist(available);
			em.getTransaction().commit();
			return true;
		
		
	}

	@Override
	public List<Feedback> showFeedback() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Feedback> query = em.createQuery("Select f from Feedback f", Feedback.class);
		List<Feedback> feedbacks = query.getResultList();
		return feedbacks;
	}




}
