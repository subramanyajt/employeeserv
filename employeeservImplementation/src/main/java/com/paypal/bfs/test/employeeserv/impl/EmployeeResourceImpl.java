package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeException;
import com.paypal.bfs.test.employeeserv.findings.Errors;
import com.paypal.bfs.test.employeeserv.model.EmployeeTable;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private InputValidator inputValidator;

    private EmployeeResourceImpl(EmployeeService employeeService, InputValidator inputValidator) {
        this.employeeService = employeeService;
        this.inputValidator = inputValidator;
    }

    @Override
    public ResponseEntity employeeGetById(String id) {
    	String message = String.format("Employee with ID %s don't exist ", id);
        Optional<Employee> employee = employeeService.byId(id);
        return employee.isPresent() ? new ResponseEntity<>(employee.get(),HttpStatus.OK)
                : new ResponseEntity<>(message,HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity createEmployee(Employee employeeRequest) {

    	String message = "";
    	
        if(null != employeeRequest.getId() ){
            Optional<Employee> employee = employeeService.byId(employeeRequest.getId()+"");
            if(employee.isPresent()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Employee Exists!");
            }
        }

        Optional<List<Errors>> error = inputValidator.getErrors(employeeRequest);
        if(error.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        boolean state = false;
        
        try {
        	state = employeeService.create(employeeRequest);
        	message = "Employee Created successfully";
        }catch(EmployeeException ex) {
        	message = ex.getMessage();
        }

        return  state ? new ResponseEntity<>(message,HttpStatus.CREATED) :
                new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);

    }



}
