package net.boundaries;

public class Customer {

	private String lineCode;
	private String customerName;
	private String service;

	public Customer() {

	}

	public Customer(String customerName) {
		this.customerName = customerName;

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
		return "customer [lineCode=" + lineCode + ", customerName=" + customerName + ", service=" + service + "]";
	}

}
