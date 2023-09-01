package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.BoardRepository;

@Controller
public class BoardController {
	private BoardRepository boardrepository;
	@GetMapping("/board")
    public String test() {
       

        return "board";
    }

}
