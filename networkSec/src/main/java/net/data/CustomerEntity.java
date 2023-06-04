package net.data;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity {
	
	@Id
	private String lineCode;
	private String customerName;
	private String service;
	
	
	public	CustomerEntity()	{
		
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getLineCode() {
		return lineCode;
	}


	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}


	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	@Override
	public String toString() {
		return "customerEntity [lineCode=" + lineCode + ", customerName=" + customerName + ", service=" + service + "]";
	}


	
	
}
