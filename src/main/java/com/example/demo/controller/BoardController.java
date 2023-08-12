package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Board;

@Controller
public class BoardController {
	@GetMapping("/PostList")
	public String PostBoard(Model model) {
		model.addAttribute("board",new Board());
		return "PostList";
	}
}
