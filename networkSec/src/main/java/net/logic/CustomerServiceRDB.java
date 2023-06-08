package net.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import net.Converter;
import net.boundaries.Customer;

import net.dal.CustomerCrud;
import net.data.CustomerEntity;


@Service
public class CustomerServiceRDB implements CustomerService {
	private CustomerCrud customerCrud;
	private Converter converter;
	private String nameFromSpringConfig;

	@Autowired
	public void setUserCrud(CustomerCrud customerCrud) {
		this.customerCrud = customerCrud;
	}

	@Autowired
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	// have spring inject a configuration to this method
	@Value("${spring.application.name:defaultAfekaDemoValue}")
	public void setNameFromSpringConfig(String nameFromSpringConfig) {
		this.nameFromSpringConfig = nameFromSpringConfig;
	}

	@PostConstruct
	public void init() {
		System.err.println("**** spring.application.name=" + this.nameFromSpringConfig);
	}

	@Override
	@Transactional
	public Customer addCustomerToDB(Customer customer) {
		String customerLinecode = PasswordManager.generateLineCodeId();
		customer.setLineCode(customerLinecode);
		
		CustomerEntity entity = this.converter.toEntity(customer);
		entity = this.customerCrud.save(entity);
		Customer c = this.converter.toBoundary(entity);
		return c;
	}

	@Override
	@Transactional(readOnly = true)
	public Customer getCustomerById(String lineCode) {
		Optional<CustomerEntity> op = this.customerCrud.findById(lineCode);

		if (op.isPresent()) {
			CustomerEntity entity = op.get();
			return this.converter.toBoundary(entity);
		} else {
			throw new RuntimeException("Could not find message by id: " + lineCode);
		}
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<CustomerEntity> entities = this.customerCrud.findAll();
		List<Customer> rv = new ArrayList<>();
		for (CustomerEntity e : entities) {
			rv.add(this.converter.toBoundary(e));
		}

		return rv;
	}

}
