package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Controller
public class FindController {
	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	 private JavaMailSender javaMailSender;
	 
	 /**
	     * 텍스트식 이메일을 보내는 메소드입니다.
	     * @param recipientEmail 수신자의 이메일 주소
	     * @param subject 이메일 제목
	     * @param text 이메일 내용
	     */
	    private void sendEmail(String recipientEmail, String subject, String text) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(recipientEmail);
	        message.setSubject(subject);
	        message.setText(text);
	        javaMailSender.send(message); // 이메일 전송
	    }

	 /**
	     * 버튼식 이메일(비밀번호 찾기)을 보내는 메소드입니다.
	     * @param recipientEmail 수신자의 이메일 주소
	     * @param subject 이메일 제목
	     * @param text 이메일 내용
	     */
	    private void sendButtonEmail2(String recipientEmail, String subject, String buttonText, String buttonLink) {
	        String htmlContent = "<html><body>" +
	        		"<p>비밀번호 재설정 버튼을 누르고 모두의 레시피 비밀번호 재설정을 진행하세요..</p>" +
	                "<a href=\"" + buttonLink + "\" style=\"display: inline-block; background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;\">"
	                + buttonText + "</a>" +
	                "</body></html>";

	        MimeMessage message = javaMailSender.createMimeMessage();
	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
	            helper.setTo(recipientEmail);
	            helper.setSubject(subject);
	            helper.setText(htmlContent, true); // true indicates HTML content
	            javaMailSender.send(message);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    // 아이디찾기 구현!!!!
	    @GetMapping("/findid")
	    public String showFindIdForm(Model model) {
	        model.addAttribute("email", "");
	        return "findid";
	    }
	    
	    @PostMapping("/findid")
	    public String findUserId(@RequestParam String email, Model model, RedirectAttributes redirectAttributes) {
	    	// 데이터베이스에서 해당 이메일을 가진 사용자의 아이디를 찾아옵니다.
	    	List<User> users = userRepository.findByemailList(email);
	    	
	    	if (!users.isEmpty()) {
	            User user = users.get(0); // 첫 번째 사용자를 선택합니다.
	            // 이 부분에서 해당 사용자의 아이디를 이메일로 보내는 로직을 구현하면 됩니다.
	            sendEmail(user.getEmail(), "모두의 레시피 아이디 인증입니다!","당신의 아이디는 " + user.getUser_id() + " 입니다!");
	            
	            redirectAttributes.addFlashAttribute("sendMessage", "이메일을 전송했습니다. 이메일을 확인해주세요!");
	            // 아이디 찾기 성공 페이지로 리다이렉트합니다.
	            return "redirect:/login";
	        } else {
	            // 아이디를 찾을 수 없는 경우 에러 메시지를 표시하고 폼을 다시 보여줍니다.
	            model.addAttribute("email", email);
	            model.addAttribute("errorMessage", "해당 이메일로 등록된 계정이 없습니다.");
	            return "findid";
	        }
	    }
	    // 아이디 찾기 기능 끝!!
	    	
	    // 비밀번호찾기 구현!!!
	    @GetMapping("/findps")
	   public String showFindPsForm(Model model) {
		   model.addAttribute("email", "");
		   return "findps";
	    }
	    
	    @PostMapping("/findps")
	    public String findUserPs(@RequestParam String email, Model model, RedirectAttributes redirectAttributes) {
	    	// 데이터베이스에서 해당 이메일을 가진 사용자의 아이디를 찾아옵니다.
	    	List<User> users = userRepository.findByemailList(email);
	    	
	    	if (!users.isEmpty()) {
	            User user = users.get(0); // 첫 번째 사용자를 선택합니다.
	            UUID uuid = UUID.randomUUID();
	            String verificationToken = uuid.toString();
	            
	            user.setVerificationToken(verificationToken);
	            userRepository.save(user);

	            // 인증 링크 생성
	            String verificationLink = "http://localhost:8080/resetPasswd/" + verificationToken;
	            // 이 부분에서 해당 사용자의 비밀번호 재설정 버튼을 이메일로 보내는 로직을 구현하면 됩니다.
	            sendButtonEmail2(user.getEmail(), "모두의 레시피 비밀번호 재설정 안내 이메일 입니다!", "비밀번호 재설정", verificationLink);
	            
	            redirectAttributes.addFlashAttribute("sendMessage", "이메일을 전송했습니다. 이메일을 확인해주세요!");
	            // 아이디 찾기 성공 페이지로 리다이렉트합니다.
	            return "redirect:/login";
	        } else {
	            // 아이디를 찾을 수 없는 경우 에러 메시지를 표시하고 폼을 다시 보여줍니다.
	            model.addAttribute("email", email);
	            model.addAttribute("errorMessage", "해당 이메일로 등록된 계정이 없습니다.");
	            return "findid";
	        }
	    }
	    
	    @GetMapping("/resetPasswd/{token}")
	    public String resetPasswd(@PathVariable String token, Model model) {
	    	model.addAttribute("token", token);
	        return "resetpasswd";
	    }
	     
	    @PostMapping("/resetpasswd")
	    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model , RedirectAttributes redirectAttributes) {
	        User user = userRepository.findByVerificationToken(token);

	        if (user != null && newPassword.equals(confirmPassword)) {
	            // 새 비밀번호를 설정합니다.
	            user.setPassword(newPassword);
	            user.setVerificationToken(null);
	            userRepository.save(user);
	            
	            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 변경이 완료되었습니다!");
	            // 비밀번호 재설정 성공 페이지로 리다이렉트합니다.
	            return "redirect:/login";
	        } else {
	            // 비밀번호 재설정 실패 시 에러 메시지를 표시하고 폼을 다시 보여줍니다.
	            model.addAttribute("token", token);
	            model.addAttribute("errorMessage", "비밀번호 재설정에 실패했습니다.");
	            return "resetpasswd";
	        }
	    }
	    // 비밀번호 찾기 구현 끝!!!!!!!!!!!!!!!!!!!!
}
