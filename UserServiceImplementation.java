package com.example.Employee_Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Employee_Management.entity.User;
import com.example.Employee_Management.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean showRegistration(User user) {
		// TODO Auto-generated method stub
		try {
			userRepository.save(user);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;	
		}
		
		
		
	}

	@Override
	public User login(String email, String password) {
		// TODO Auto-generated method stub
		  User validUser=userRepository.findByEmail(email);
			
			if(validUser!=null && validUser.getPassword().equals(password)) {
				return validUser;
			}
			return null;
	}

	

}
