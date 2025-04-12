package com.example.Employee_Management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.Employee_Management.entity.Employee;
import com.example.Employee_Management.entity.User;

public interface EmployeeService {
	
	public boolean showRegisterPage(Employee employee);

	public boolean showAddEmployee(Employee employee);

	public List<Employee> getAllEmployeesList();
	
	public Employee getEmployeeById(int id);
	
	public boolean updateEmployee(Employee employee);

	public List<Employee> getAllEmployees();

	public Employee login(String email, String password);

	public List<Employee> searchByField(String field, String keyword);

//	public List<Employee> filterEmployees(String name, String email, String department, String position, String address,
//			Double salary, LocalDate dateOfJoining, String empType);

}
