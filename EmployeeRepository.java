package com.example.Employee_Management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee_Management.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Employee findByEmail(String email);

	List<Employee> findByNameContainingIgnoreCase(String keyword);

	List<Employee> findBySalary(double double1);

	List<Employee> findByDepartmentContainingIgnoreCase(String keyword);

	List<Employee> findByPositionContainingIgnoreCase(String keyword);
	
	List<Employee> findBydateOfJoining(LocalDate localDate);

	List<Employee> findByempType(String keyword);



}
