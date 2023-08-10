package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	@Query(value = "SELECT * FROM user WHERE user_id = :user_id AND password = :password", nativeQuery = true)
    User findByUserIdAndPassword(String user_id, String password);
}
