package com.example.Employee_Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Employee_Management.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	

}
