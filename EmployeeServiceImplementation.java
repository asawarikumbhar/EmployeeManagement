package com.example.Employee_Management.service;


import java.util.Objects;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Employee_Management.entity.Employee;
import com.example.Employee_Management.entity.User;
import com.example.Employee_Management.repository.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public boolean showRegisterPage(Employee employee) {
		// TODO Auto-generated method stub
		
		try {
			employeeRepository.save(employee);
			return true;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public boolean showAddEmployee(Employee employee) {
		// TODO Auto-generated method stub
		try {
			employeeRepository.save(employee);
			return true;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public List<Employee> getAllEmployeesList() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(int id) {
		// TODO Auto-generated method stub
	  return employeeRepository.findById(id).orElse(null);
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		if(employeeRepository.existsById(employee.getId())) {
			try {
				employeeRepository.save(employee);
				return true;
			}
			catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public Employee login(String email, String password) {
		// TODO Auto-generated method stub
		Employee validUser=employeeRepository.findByEmail(email);
		
		if(validUser!=null && validUser.getPassword().equals(password)) {
			return validUser;
		}
		return null;
	}
	
	
	
	public List<Employee> searchByField(String field, String keyword) {
	    switch (field) {
	        case "name":
	            return employeeRepository.findByNameContainingIgnoreCase(keyword);
	        case "salary":
	            return employeeRepository.findBySalary(Double.parseDouble(keyword));
	        case "department":
	            return employeeRepository.findByDepartmentContainingIgnoreCase(keyword);
	        case "position":
	            return employeeRepository.findByPositionContainingIgnoreCase(keyword);
	        case "joiningDate":
	            return employeeRepository.findBydateOfJoining(LocalDate.parse(keyword));
	        case "gender":
	            return employeeRepository.findByempType(keyword);
	        default:
	            return new ArrayList<>();
	    }
	}
	
}
