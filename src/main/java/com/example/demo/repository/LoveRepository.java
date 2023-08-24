package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Love;

public interface LoveRepository extends JpaRepository<Love, Long>{
    List<Love> findByOrderByLoveId();
    
}