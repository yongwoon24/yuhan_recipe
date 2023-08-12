package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long>{

}