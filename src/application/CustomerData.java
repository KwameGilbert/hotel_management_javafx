package application;

import java.sql.Date;

public class CustomerData {

	private int customerID;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private int roomNumber;
	private Double total;
	private Date checkInDate;
	private Date checkOutDate;

	public CustomerData(int customerID, String firstName, String lastName, String phoneNumber, int roomNumber, Double total,
			Date checkInDate, Date checkOutDate) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.roomNumber = roomNumber;
		this.total = total;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}
	
	public Double getTotal() {
		return total;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

}
