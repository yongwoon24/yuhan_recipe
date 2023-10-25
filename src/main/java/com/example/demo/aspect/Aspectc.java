package com.example.demo.aspect;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Board;
import com.example.demo.entity.Love;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.LoveRepository;
import com.example.demo.repository.RecipeRepository;

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
        }
    }
}
