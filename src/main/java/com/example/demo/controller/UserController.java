package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.ScrapRepository;
import com.example.demo.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private LoveRepository loveRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    //여기도 추가함!!!!!!!!!!!!!!!!!!!!!
    @Autowired
    private JavaMailSender javaMailSender;
    
    @GetMapping("/user")
    public String listUsers(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    	String loggedInNickname = (String) session.getAttribute("loggedInNickname");
    	if("관리자".equals(loggedInNickname)) {
	        List<User> users = userRepository.findAll();
	        model.addAttribute("users", users);
	        return "userList";
    	}else {
    		return "redirect:/";
    	}
    }
    
    @GetMapping("/signup")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
   
    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
    	try {
            // 사용자가 존재하지 않을 때만 토큰 생성 및 저장, 이메일 전송
            UUID uuid = UUID.randomUUID();
            String verificationToken = uuid.toString();

            user.setVerificationToken(verificationToken);
            String photo = "/img/기본유저1.jpg";
            user.setUserphotopath(photo);
            userRepository.save(user);

            // 인증 링크 생성
            String verificationLink = "http://localhost:8080/verifyEmail/" + verificationToken;

            // 이메일 전송 메소드 호출
            sendButtonEmail(user.getEmail(), "모두의 레시피 이메일 인증입니다!", "인증하기", verificationLink);
            
            // 성공 시 메시지 설정
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 이메일을 확인하세요!");
            
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
    
    @GetMapping("/deleteUser/{nickname}")
    @Transactional // 트랜잭션 설정
    public String deleteUser(@PathVariable String nickname) {
        User user = userRepository.findbynickname(nickname);
        List<Board> board = boardRepository.findByNickname(nickname);
        List<Integer> postIds = new ArrayList<>();
        
        for (int i = 0; i < board.size(); i++) {
			postIds.add(board.get(i).getPostId());
		}
        for (int i = 0; i < postIds.size(); i++) {
			int postId = postIds.get(i);
			commentRepository.deleteByPostId(postId);
		}
        
        boardRepository.deleteByNickname(nickname);
        scrapRepository.deleteByUser(user); // 사용자에 해당하는 스크랩 삭제
        loveRepository.deleteByUser(user);
        userRepository.deleteById(user.getUser_id());
        recipeRepository.deleteByNickname(nickname);
        return "redirect:/user";
    }
   
    
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
    
    @GetMapping("/checkUsernicknameAvailability")
    @ResponseBody
    public boolean checkUsernicknameAvailability(@RequestParam String nickname) {
        // 데이터베이스에서 해당 사용자 이름을 검색하여 결과 확인
       List<User> existingUser = userRepository.findBynicknameList(nickname);
        
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
    
    
    
    
    
   
}