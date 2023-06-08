package net.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import net.boundaries.Customer;
import net.logic.CustomerService;


@RestController
@CrossOrigin
public class CustomerController {

    private CustomerService customers;
    
    @Autowired
	public CustomerController(CustomerService customers) {
		this.customers = customers;
	}

    @RequestMapping(
			path = { "/customers" },
			method = { RequestMethod.POST },
			produces = {MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE }
			)
	public Customer createCustomer(@RequestBody Customer customer)  {
		return this.customers.addCustomerToDB(customer);
	
    }
    
    @RequestMapping(path = { "/customers" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Customer[] hello() {
		List<Customer> rv = this.customers.getAllCustomers();
		return rv.toArray(new Customer[0]);
	}
    
    
    
    
    
}