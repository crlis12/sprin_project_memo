package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RequestMapping("/post")
@RestController
public class PostRestController {

	
	@Autowired
	private PostBO postBO;
	
	/**
	 * 글쓰기 생성
	 * @param subject
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request)	{
		
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId"); // 글이 저장될시 저장될 아이디
		
		// BO create
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		int row = postBO.addPost(userLoginId, userId, subject, content, file);
		
		if(row < 1) {
			result.put("result", "error");
		} 
		
		return result; 
		
	}
	
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value="file", required = false) MultipartFile file,
			HttpSession session) {
			
			int userId	= (int)session.getAttribute("userId");
			String userloginId	= (String)session.getAttribute("userLoginId");
			
			// db update
			int row = postBO.updatePost(userloginId, userId, postId, subject, content, file);
			
			Map<String, Object> result = new HashMap<>();
			result.put("result", "success");
			
			if(row< 1) {
				result.put("result", "error");
				result.put("error_message", "글 수정에 실패했습니다. 다시 작성해주세요");
			}
			
			
			
		return result;
	}
	
}
