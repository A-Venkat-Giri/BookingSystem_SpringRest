package com.dev.bss.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="booking_info")
public class Booking {
	@Id @Column(name="booking_id") @GeneratedValue(strategy=GenerationType.AUTO)
	private int bookingId;
	
	@Column(name ="bus_id")
	private int busId;
	private String source;
	private String destination;
	@Temporal(value = TemporalType.DATE)
	private Date date;
	private int numOfSeats;
	@Column(name = "user_id")
	private int userId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String Destination) {
		this.destination = Destination;
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getNumOfSeats() {
		return numOfSeats;
	}
	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}
	
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", busId=" + busId + ", source=" + source + ", destination="
				+ destination + ", date=" + date + ", numOfSeats=" + numOfSeats + "]";
	}

      
	

}
