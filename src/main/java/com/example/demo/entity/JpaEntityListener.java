package com.example.demo.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.PrePersist;

public class JpaEntityListener {
	@PrePersist
    public void prePersist(Recipe recipe) {
        //recipe.setCreated_date(new LocalDate());  // 작성 날짜 설정
    }
}
