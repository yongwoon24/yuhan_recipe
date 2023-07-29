package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "list";
    }
    
    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create";
    }
    
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/";
    }
    
    @GetMapping("/edit/{user_id}")
    public String editUserForm(@PathVariable String user_id, Model model) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("Invalid User ID: " + user_id));
        model.addAttribute("user", user);
        return "edit";
    }
    
    @PostMapping("/edit/{user_id}")
    public String editUser(@PathVariable String user_id, @ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/";
    }
    
    @GetMapping("/delete/{user_id}")
    public String deleteUser(@PathVariable String user_id) {
        userRepository.deleteById(user_id);
        return "redirect:/";
    }
}