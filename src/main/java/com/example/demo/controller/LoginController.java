package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	 @Autowired
	 private UserRepository userRepository;
     
	  @GetMapping("/login")
	     public String showLoginForm(Model model) {
	         model.addAttribute("user", new User());
	         return "login";
	     }
	     
	     @PostMapping("/login")
	     public String loginUser(@RequestParam String user_id, @RequestParam String password, RedirectAttributes redirectAttributes, HttpSession session) {
	         // 사용자 정보를 데이터베이스에서 조회합니다.
	         List<User> users = userRepository.findByUser_id(user_id);
	
	         if (!users.isEmpty()) {
	             User user = users.get(0); // 첫 번째 사용자를 선택합니다.
	
	             if (!user.isEmailVerified()) {
	                 // 이메일 인증이 완료되지 않은 상태일 때 처리
	                 redirectAttributes.addFlashAttribute("errorMessage", "이메일 인증이 완료되지 않았습니다!");
	                 return "redirect:/login"; // 이메일 인증 실패 시 표시할 페이지
	             }
	
	             if (password.equals(user.getPassword())) {
	                 // 비밀번호가 일치하는 경우 로그인 성공 처리
	                 // 로그인 성공 처리를 수행하고 리다이렉트 또는 페이지 이동을 설정하세요.
	                
	                // 로그인 성공 시 세션에 사용자 정보 저장
	                 session.setAttribute("loggedInNickname", user.getNickname());
	                 session.setAttribute("loggedInUserId", user.getUser_id());
	                 return "redirect:/"; // 로그인 후 이동할 페이지
	             }
	         }
	
	         // 로그인 실패 처리를 수행하고 리다이렉트 또는 페이지 이동을 설정하세요.
	         // 예: 에러 메시지 표시, 로그인 실패 페이지 이동 등
	         redirectAttributes.addFlashAttribute("errorMessage", "아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.\r\n"
	               + "입력하신 내용을 다시 확인해주세요.");
	         return "redirect:/login"; // 로그인 실패 시 표시할 페이지
	     }
	
	     @GetMapping("/logout")
	     public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
	         // 세션에서 사용자 정보 가져오기
	         String loggedInNickname = (String) session.getAttribute("loggedInNickname");
	
	         // 세션에 loggedInNickname가 있는지 확인
	         if (loggedInNickname != null) {
	             // 세션에 로그인 정보가 있을 경우, 세션 삭제 후 로그인 페이지로 이동
	             session.removeAttribute("loggedInNickname");
	             return "redirect:/";
	         } else {
	             // 세션에 로그인 정보가 없을 경우, signup 페이지로 이동
	            redirectAttributes.addFlashAttribute("loginMessage", "로그인 상태가 아닙니다!");
	             return "redirect:/login";
	         }
	     }

}
