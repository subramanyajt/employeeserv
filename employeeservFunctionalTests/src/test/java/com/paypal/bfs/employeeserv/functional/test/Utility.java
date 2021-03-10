package com.paypal.bfs.employeeserv.functional.test;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

public class Utility {
	
	public static Employee getEmployee() {
	    Employee employee = new Employee();
	    //employee.setId(Integer.valueOf(1));
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

}
