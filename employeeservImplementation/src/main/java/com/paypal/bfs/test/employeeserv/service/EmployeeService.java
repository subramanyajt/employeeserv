package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.exception.EmployeeException;
import com.paypal.bfs.test.employeeserv.model.EmployeeTable;
import com.paypal.bfs.test.employeeserv.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    private final Map<Integer, Employee> employeeMap = new ConcurrentHashMap<>();

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    /**
    * Method to fetch employee details based on id first it will check in cache if found the return else fetch from DB cache it and return
    * Caching is done to improve the performance
    **/
    public Optional<Employee> getById(String id){

        Employee employee = employeeMap.get(Integer.valueOf(id));
        if(Objects.nonNull(employee)){
            return Optional.of(employee);
        }

        Optional<EmployeeTable> et = employeeRepository.findById(Integer.valueOf(id));
        if(et.isPresent()){
            Employee e = Util.forAPI(et.get());
            if(Objects.nonNull(e)){
                employeeMap.put(e.getId(),e);
                return Optional.of(e);
            }

        }
        return Optional.empty();
    }

    /**
    * Method to save employee data into databse
    **/
    public boolean createEmployee(Employee employeeRequest) throws EmployeeException{
        EmployeeTable employeeTable = null;
        try{
            employeeTable = employeeRepository.save(Util.forDB(employeeRequest));
            if(Objects.nonNull(employeeTable)){
                return true;
            }
        }catch(Exception e){
            System.out.println("Error in table creating...");
            throw new EmployeeException("Cannot create the employee");
        }

        return false;
    }

}
