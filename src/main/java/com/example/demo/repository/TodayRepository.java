package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Today;
import com.example.demo.entity.User;

public interface TodayRepository extends JpaRepository<Today, Integer>{
	@Query("SELECT MAX(r.id) FROM Today r")
	int maxTodayId();
	
	Today findById(int tId);
	
	Today findByUser(User user);
	
	void deleteByUser(User user);
}
