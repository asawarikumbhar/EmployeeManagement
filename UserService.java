package com.example.Employee_Management.service;

import com.example.Employee_Management.entity.Employee;
import com.example.Employee_Management.entity.User;

public interface UserService {
	
   public boolean showRegistration(User user);

   public User login(String email, String password);

  // public Employee login(String email, String password);

  
}
