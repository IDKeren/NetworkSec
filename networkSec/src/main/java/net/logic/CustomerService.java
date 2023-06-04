package net.logic;

import java.util.List;

import net.boundaries.Customer;


public interface CustomerService {

	public Customer addCustomerToDB(Customer customer);
	public Customer getCustomerById(String lineCode);
	public List<Customer> getAllCustomers();	
	
}
