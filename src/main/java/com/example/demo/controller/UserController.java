package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/user")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }
    
    @GetMapping("/signup")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/login";
    }
    
    @GetMapping("/editUser/{user_id}")
    public String editUserForm(@PathVariable String user_id, Model model) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("Invalid User ID: " + user_id));
        model.addAttribute("user", user);
        return "editUser";
    }
    
    @PostMapping("/editUser/{user_id}")
    public String editUser(@PathVariable String user_id, @ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/user";
    }
    
    @GetMapping("/deleteUser/{user_id}")
    public String deleteUser(@PathVariable String user_id) {
        userRepository.deleteById(user_id);
        return "redirect:/user";
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String user_id, @RequestParam String password) {
        User user = userRepository.findByUserIdAndPassword(user_id, password);
        if (user != null) {
            // 로그인 성공 처리
            return "redirect:/"; // 로그인 후 이동할 페이지 지정
        } else {
            // 로그인 실패 처리
            return "redirect:/login?error=true";
        }
    }
    
    @GetMapping("/checkUserIdAvailability")
    @ResponseBody
    public boolean checkUserIdAvailability(@RequestParam String user_id) {
        // 데이터베이스에서 해당 사용자 이름을 검색하여 결과 확인
       List<User> existingUser = userRepository.findByUser_id(user_id);
        
        // 중복 여부에 따라 결과 반환
        return existingUser.isEmpty(); // true는 중복이 아님, false는 중복임
    }
}