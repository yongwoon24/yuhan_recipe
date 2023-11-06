package com.example.demo.aspect;

import java.util.List;
import java.util.Random;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Board;
import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Today;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.TodayRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class Aspectc {

	@Autowired
	private LoveRepository loveRepository;
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	HttpSession session;
	@Autowired
	private UserRepository userR;
	@Autowired
	private TodayRepository todayR;
	
	@Before("execution(* com.example.demo.controller..*.*(..))")
    public void beforeAlarmMethodExecution() {
        String loggedInNickname = (String) session.getAttribute("loggedInNickname");
        String loggedInUserId = (String) session.getAttribute("loggedInUserId");

        if (loggedInNickname != null) {
            List<Recipe> recipes = recipeRepository.findByNicknameOrderByCreateddateDesc(loggedInNickname);
            List<Board> boards = boardRepository.findByNickname(loggedInNickname);

            List<Love> lovesT = loveRepository.findLovesByActivityNotEqualAndBoardInOrRecipeInOrderByDateAtDesc1(boards, recipes, loggedInUserId);
            // 읽지 않은 알람이 있을 때
            if (!lovesT.isEmpty() && lovesT != null) {
                session.setAttribute("alarm", 1);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                for (int i = 0; i < lovesT.size(); i++) {
                    System.out.println(lovesT.get(i).getActivityId());
                }
            } else {
                session.setAttribute("alarm", 2);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
            
            User user = userR.findbynickname(loggedInNickname);
            Today today = todayR.findByUser(user);
            Random random = new Random();
        	int max = recipeRepository.maxRecipeId();
            int min = 1;
            int[] nums = new int[10];
			 for (int j = 0; j < 10; j++) {
			        int randomnum;
			        Recipe recipe;
			        
			        do {
			            randomnum = random.nextInt(max - min + 1) + min;
			            recipe = recipeRepository.findById(randomnum);
			        } while (recipe == null);
			        
			        nums[j] = randomnum;
			    }
			 if(today == null) {
				 Today today1 = new Today();
					today1.setNo1(nums[0]);
					today1.setNo2(nums[1]);
					today1.setNo3(nums[2]);
					today1.setNo4(nums[3]);
					today1.setNo5(nums[4]);
					today1.setNo6(nums[5]);
					today1.setNo7(nums[6]);
					today1.setNo8(nums[7]);
					today1.setNo9(nums[8]);
					today1.setNo10(nums[9]);
					todayR.save(today1);}
					}
        }
    }

