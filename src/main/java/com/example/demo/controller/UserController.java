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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    //여기도 추가함!!!!!!!!!!!!!!!!!!!!!
    @Autowired
    private JavaMailSender javaMailSender;
    
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
    	try {
            // 사용자가 존재하지 않을 때만 토큰 생성 및 저장, 이메일 전송
            UUID uuid = UUID.randomUUID();
            String verificationToken = uuid.toString();

            user.setVerificationToken(verificationToken);
            userRepository.save(user);

            // 인증 링크 생성
            String verificationLink = "http://localhost:8080/verifyEmail/" + verificationToken;

            // 이메일 전송 메소드 호출
            sendButtonEmail(user.getEmail(), "모두의 레시피 이메일 인증입니다!", "인증하기", verificationLink);

            return "redirect:/login";
        } catch (Exception e) {
            throw new RuntimeException("회원가입 중 오류 발생", e);
        }
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
    public String loginUser(@RequestParam String user_id, @RequestParam String password, Model model) {
        // 사용자 정보를 데이터베이스에서 조회합니다.
        List<User> users = userRepository.findByUser_id(user_id);

        if (!users.isEmpty()) {
            User user = users.get(0); // 첫 번째 사용자를 선택합니다.

            if (user.isEmailVerified() && password.equals(user.getPassword())) {
                // 이메일이 인증되었고 비밀번호가 일치하는 경우 로그인 성공
                // 로그인 성공 처리를 수행하고 리다이렉트 또는 페이지 이동을 설정하세요.
                // 예: 로그인 세션 생성, 로그인 상태로 리다이렉트 등
                return "redirect:/"; // 로그인 후 이동할 페이지
            }
        }

        // 로그인 실패 처리를 수행하고 리다이렉트 또는 페이지 이동을 설정하세요.
        // 예: 에러 메시지 표시, 로그인 실패 페이지 이동 등
        model.addAttribute("loginError", true);
        return "login"; // 로그인 실패 시 표시할 페이지
    }

    
   /*
    * @GetMapping("/checkDuplicateId")
    * 
    * @ResponseBody public String checkDuplicateId(@RequestParam String user_id) {
    * User existingUser = userRepository.findById(user_id).orElse(null); if
    * (existingUser != null) { return "duplicate"; } else { return "available"; } }
    */
    
    @GetMapping("/checkUserIdAvailability")
    @ResponseBody
    public boolean checkUserIdAvailability(@RequestParam String user_id) {
        // 데이터베이스에서 해당 사용자 이름을 검색하여 결과 확인
       List<User> existingUser = userRepository.findByUser_id(user_id);
        
        // 중복 여부에 따라 결과 반환
        return existingUser.isEmpty(); // true는 중복이 아님, false는 중복임
    }
    
    @GetMapping("/checkUseremailAvailability")
    @ResponseBody
    public boolean checkUseremailAvailability(@RequestParam String email) {
        // 데이터베이스에서 해당 사용자 이름을 검색하여 결과 확인
       List<User> existingUser = userRepository.findByemailList(email);
        
        // 중복 여부에 따라 결과 반환
        return existingUser.isEmpty(); // true는 중복이 아님, false는 중복임
    }
    
    //이메일 인증 수정!!!!!!!!!!!!!!!!!!!
    
    
    /**
     * 이메일 인증을 처리하는 엔드포인트입니다.
     * @param token 인증에 사용되는 토큰
     * @return 인증 성공 시 이동할 페이지 또는 인증 실패 시 이동할 페이지
     */
    @GetMapping("/verifyEmail/{token}")
    public String verifyEmail(@PathVariable String token) {
    	User user = userRepository.findByVerificationToken(token);

        if (user != null) {
            // 사용자의 이메일을 인증된 상태로 표시합니다.
            user.setEmailVerified(true);
            user.setVerificationToken(null); // 토큰을 초기화합니다.
            userRepository.save(user);

            // 성공 페이지로 리디렉션합니다.
            return "redirect:/login";
        } else {
            // 인증 실패 페이지로 리디렉션합니다.
            return "redirect:/signup";
        }
    }

    /**
     * 버튼식 이메일을 보내는 메소드입니다.
     * @param recipientEmail 수신자의 이메일 주소
     * @param subject 이메일 제목
     * @param text 이메일 내용
     */
    private void sendButtonEmail(String recipientEmail, String subject, String buttonText, String buttonLink) {
        String htmlContent = "<html><body>" +
        		"<p>인증하기 버튼을 누르고 모두의 레시피 가입을 진행하세요..</p>" +
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

    //여기까지 수정!!!!!!!!!!!!!!!!!!!!!!
    
    // 아이디찾기 구현!!!!
    @GetMapping("/findid")
    public String showFindIdForm(Model model) {
        model.addAttribute("email", "");
        return "findid";
    }
    
    @PostMapping("/findid")
    public String findUserId(@RequestParam String email, Model model) {
    	// 데이터베이스에서 해당 이메일을 가진 사용자의 아이디를 찾아옵니다.
    	List<User> users = userRepository.findByemailList(email);
    	
    	if (!users.isEmpty()) {
            User user = users.get(0); // 첫 번째 사용자를 선택합니다.
            // 이 부분에서 해당 사용자의 아이디를 이메일로 보내는 로직을 구현하면 됩니다.
            sendEmail(user.getEmail(), "모두의 레시피 아이디 인증입니다!","당신의 아이디는 " + user.getUser_id() + " 입니다!");

            // 아이디 찾기 성공 페이지로 리다이렉트합니다.
            return "redirect:/login";
        } else {
            // 아이디를 찾을 수 없는 경우 에러 메시지를 표시하고 폼을 다시 보여줍니다.
            model.addAttribute("email", email);
            model.addAttribute("errorMessage", "해당 이메일로 등록된 아이디가 없습니다.");
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
    public String findUserPs(@RequestParam String email, Model model) {
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

            // 아이디 찾기 성공 페이지로 리다이렉트합니다.
            return "redirect:/login";
        } else {
            // 아이디를 찾을 수 없는 경우 에러 메시지를 표시하고 폼을 다시 보여줍니다.
            model.addAttribute("email", email);
            model.addAttribute("errorMessage", "해당 이메일로 등록된 아이디가 없습니다.");
            return "findid";
        }
    }
    
    @GetMapping("/resetPasswd/{token}")
    public String resetPasswd(@PathVariable String token, Model model) {
    	model.addAttribute("token", token);
        return "resetpasswd";
    }
     
    @PostMapping("/resetpasswd")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
        User user = userRepository.findByVerificationToken(token);

        if (user != null && newPassword.equals(confirmPassword)) {
            // 새 비밀번호를 설정합니다.
            user.setPassword(newPassword);
            user.setVerificationToken(null);
            userRepository.save(user);
            
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