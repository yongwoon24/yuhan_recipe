package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.demo.entity.User;
import java.util.List;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, String> {
   @Query(value = "SELECT * FROM user WHERE user_id = :user_id AND password = :password", nativeQuery = true)
    User findByUserIdAndPassword(String user_id, String password);
   @Query(value = "SELECT * FROM user WHERE user_id = :user_id ", nativeQuery = true)
    List<User> findByUser_id(String user_id);
   @Query(value = "SELECT * FROM user WHERE email = :email ", nativeQuery = true)
   List<User> findByemailList(String email);
   @Query(value = "SELECT * FROM user WHERE nickname = :nickname ", nativeQuery = true)
   List<User> findBynicknameList(String nickname);
   
   User findByVerificationToken(String verificationToken);
   User findByEmail(String email);
   //User findByUser_id(String user_id);
  
   

   
}