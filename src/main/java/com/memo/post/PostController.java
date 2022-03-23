package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;


@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private PostBO postBO;
	
	@RequestMapping("/post_list_view")
	public String postListView(Model model, HttpServletRequest request) {
		// 세션의 값을 확인해서 지금 로그인이 되었는지 확인
		// ->비로그인: 로그인 페이지로 리다이렉트
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId"); // 유저 아이디를 가지고 온다 유저가 쓴 글목록만 보여지게 하기 위해서
		
		if(userId == null) { // 비로그인 상태
			return "redirect:/user/sign_in_view";
		}
		
		List<Post> postList = postBO.getPostListByUserId(userId); // 유저 아이디에 해당하는 유저 글 목록을 가지고 온다
		
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/post_list");
		
		return "template/layout";
	}
	
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model) {
		// 세션이 있는 경우에만 글쓰기 가능
		model.addAttribute("viewName", "post/post_create");
		
		
		return "template/layout";
	}
}
