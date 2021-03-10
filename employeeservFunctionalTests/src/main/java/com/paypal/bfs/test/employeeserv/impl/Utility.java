package com.paypal.bfs.test.employeeserv.impl;

public class Utility {
	
	public static Employee getEmployee() {
	    Employee employee = new Employee();
	    employee.setId(Integer.valueOf(1));
	    employee.setFirstName("JT");
	    employee.setLastName("subbu");
	    employee.setDateOfBirth("01-01-1991");
	    employee.setAddressLine1("cvramannagar");
	    employee.setCity("bangalore");
	    employee.setState("karnataka");
	    employee.setZipCode("560092");
	    employee.setCountry("India");
	    return employee;
	}
	
	public static String getResult() {
		String expected = 		"{"+
				   " \"id\": 1,"+
				   " \"first_name\": \"JT\","+
				   " \"last_name\": \"subbu\","+
				   " \"date_of_birth\": \"01-01-1991\","+
				   " \"address_line1\": \"cvramannagar\","+
				   " \"city\": \"bangalore\","+
				   " \"state\": \"karnataka\","+
				   " \"country\": \"India\","+
				   " \"zip_code\": \"560092\""+
				"}";
		return expected;
	}

}
