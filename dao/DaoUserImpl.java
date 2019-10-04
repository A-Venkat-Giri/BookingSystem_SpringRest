package com.dev.bss.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.dev.bbs.exceptions.AddUserException;
import com.dev.bbs.exceptions.BookingFailedException;
import com.dev.bbs.exceptions.CancelFailedException;
import com.dev.bbs.exceptions.CustomException;
import com.dev.bbs.exceptions.DeleteFailedException;
import com.dev.bbs.exceptions.TicketRetrievalFailedException;
import com.dev.bbs.exceptions.UpdateFailedException;
import com.dev.bss.beans.Available;
import com.dev.bss.beans.Booking;
import com.dev.bss.beans.Bus;
import com.dev.bss.beans.Feedback;
import com.dev.bss.beans.Ticket;
import com.dev.bss.beans.User;

@Repository
public class DaoUserImpl implements DaoUser {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQLUnit");

	@Override
	public User createUser(User user) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			return user;

			
				
			
		} catch (Exception e) {
			throw new AddUserException("FailedToAddUser");
		}
		

	}

	@Override
	public Boolean updateUser(User user, String password ) {
		try {
			EntityManager em = emf.createEntityManager();

			Boolean login = loginUser(user.getUserId(), password);
			if(login)
			{

				em.getTransaction().begin();
				User user1 = em.find(User.class, user.getUserId());
				user1.setUserName(user.getUserName());
				user1.setEmail(user.getEmail());
				user1.setContact(user.getContact());
				user1.setPassword(user.getPassword());
				em.getTransaction().commit();
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			throw new UpdateFailedException("FailedTOUpdate");
		}


	}

	@Override
	public Boolean deleteUser(int userId, String password) {
		try {
			EntityManager em = emf.createEntityManager();
			Boolean login = loginUser(userId, password);
			if(login)
			{
				em.getTransaction().begin();
				User user = em.find(User.class,userId );
				em.remove(user);
				em.getTransaction().commit();
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			throw new DeleteFailedException("DeleteFailed");
		}
	}

	@Override
	public Boolean loginUser(int userId, String password) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("from User u where u.userId= :userid and u.password= :password ");
			query.setParameter("userid", userId );
			query.setParameter("password", password);
			em.getTransaction().begin();
			List user=query.getResultList();
			em.getTransaction().commit();
			User user1= (User) user.get(0);
			return true;
		} catch (Exception e) {
			throw new com.dev.bbs.exceptions.LoginException("LoginFAILED");
		}
	}

	@Override
	public User searchUser(int userId) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("from User u where u.userId= :userid ");
			query.setParameter("userid", userId );
			em.getTransaction().begin();
			List user=query.getResultList();
			em.getTransaction().commit();
			User user1= (User) user.get(0);
			return user1;
		} catch (Exception e) {
			throw new CustomException("FailedToGetUser");
		}

	}


	@Override
	public Booking bookTicket(Ticket ticket) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query =em.createQuery("Update Available a SET a.availId = :availseats where a.bus_id= :busid");
			System.out.println("update");
			Integer avaiSeats = ticket.getAvailable() - ticket.getNumberOfSeats();
			System.out.println("set param");
			query.setParameter("availseats",avaiSeats );
			query.setParameter("busid", ticket.getBusId());
			Integer i = query.executeUpdate();
			System.out.println("execute");
			em.getTransaction().commit();
			System.out.println("update done");em.close();
			
			Boolean state = false;
			if(i>0)
			{
				EntityManager em1 = emf.createEntityManager();
				Booking booking = new Booking();
				booking.setUserId(ticket.getUserId());
				booking.setBusId(ticket.getBusId());
				booking.setDate(ticket.getDate());
				booking.setDestination(ticket.getDestination());
				booking.setSource(ticket.getSource());
				booking.setNumOfSeats(ticket.getNumberOfSeats());
				em1.getTransaction().begin();
				System.out.println("after begin");
				em1.persist(booking);
				System.out.println("before commit");
				em1.getTransaction().commit();
				System.out.println("after commit");
				return booking;	
			}
			else {
				return null;
			}
		} catch (Exception e) {
			throw new BookingFailedException("TicketBookFailed");
		}
	}

	@Override
	public Boolean cancelTicket(int bookingId) {
		Boolean state;
		try {
			EntityManager em  =  emf.createEntityManager();
			Booking booking = getTicket(bookingId);
			int busId = booking.getBusId();
			int seats = booking.getNumOfSeats();
			em.getTransaction().begin();
			Query query = em.createQuery("DELETE FROM Booking b where b.bookingId = :bookingid");
			query.setParameter("bookingid", bookingId );
			state = false;
			Integer i = query.executeUpdate();
			if(i>0)
			{
				TypedQuery<Available> q = em.createQuery("FROM Available a where a.bus_id = :busId",Available.class);
				q.setParameter("busId", busId );
				List<Available> available = q.getResultList();
				Available avail = available.get(0);
				int availSeats = avail.getAvailSeats();
				availSeats = availSeats + seats;
				Query q1 = em.createQuery("UPDATE Available a set a.availSeats = :avail where a.bus_id = :busid");
				q1.setParameter("avail", availSeats);
				q1.setParameter("busid", busId);
				Integer i1 = q1.executeUpdate();
				em.getTransaction().commit();
				if(i1 > 0 )
				{
					state = true;
				}
			}
		} catch (Exception e) {
			throw new CancelFailedException("NotAbleToCancel");
		}
		return state;
	}

	@Override
	public Booking getTicket(int bookingId) {
		try {
			EntityManager em = emf.createEntityManager();
			TypedQuery<Booking> query = em.createQuery("From Booking b where b.bookingId = :bookingid",Booking.class);
			query.setParameter("bookingid",bookingId );
			List<Booking> booking1 = query.getResultList();
			Booking booking = booking1.get(0);
			return booking;
		} catch (Exception e) {
			throw new TicketRetrievalFailedException("GetTicketFailedException");
		}
		
	}

	@Override
	public Integer checkAvailability(int busId, Date date) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("Select a.availSeats from Available a where a.bus_id = :busid and a.journeyDate = :date");
			query.setParameter("busid", busId);
			query.setParameter("date", date);
			List<Integer> availability = query.getResultList();
			System.out.println(availability);
			Integer seats = availability.get(0);

			return seats;
		} catch (Exception e) {
			throw new CustomException("CheckingFailedException");
		}
	}

	@Override
	public List<Bus> searchBus(String source, String destination, Date date) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query =em.createQuery("Select b From Bus b inner join Available a ON b.busId = a.bus_id where b.source = :source"
					+ " and b.destination = :destination and a.journeyDate =:date ");
			query.setParameter("source", source);
			query.setParameter("destination", destination);
			query.setParameter("date", date);

			List<Bus> buses = query.getResultList();

			return buses;
		} catch (Exception e) {
			throw new CustomException("SearchBusException");
		}
	}

	@Override
	public Boolean giveFeedBack(Feedback feedback) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(feedback);
		em.getTransaction().commit();
		return true;
	}

	@Override
	public List<Booking> getAllTickets(int userId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<Booking> query = em.createQuery("From Booking b where b.userId = :bid", Booking.class);
		query.setParameter("bid", userId);
		List<Booking> bookings = query.getResultList();
		return bookings;
		
	}

	@Override
	public Booking printTicket(int booking_id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Booking book = em.find(Booking.class,booking_id );
		if(book!=null)
		{
//			Booking booking=new Booking();
//			booking.setBookingId(book.getBookingId());
//			booking.setBusId(book.getBusId());
//			booking.setDate(book.getDate());
//			booking.setSource(book.getSource());
			return book;
		}
		return null;
	}

}
