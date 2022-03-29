package com.memo.post.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	// 로그 찍기
	// private Logger logger = LoggerFactory.getLogger(PostBO.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// select
	public List<Post> getPostListByUserId(int userId){
		return postDAO.selectPostListByUserId(userId);
	}
	
	public Post getPostById(int postId)	{
		return postDAO.selectPostById(postId);
	}
	
	
	// insert
	public int addPost(String loginId, int userId,
			String subject, String content, MultipartFile file) {
		String imagePath = null;
		if(file !=null) {
			try {
				imagePath = fileManagerService.saveFile(loginId, file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return postDAO.insertPost(userId, subject, content, imagePath);
		
	}
	
	//update
	public int updatePost(String userLoginId, int userId, int postId, String subject, String content, MultipartFile file) {
		
		Post post = this.getPostById(postId);
		if(post == null) {
			//System.out.println(""); // 웹에서는 사용하면 안되다 쓰레드가 멈추기 때문에 사용하지 않는다 동시에 3명의 사용자가 접속하면 한명의 사용자만 보여지고 나머지는 동작이 맘추기 떄문
			logger.error("[update post] 수정할 메모가 존재하지 않음. postId:{}, userId{}", postId, userId);
			return 0;
		}
		
		String imagePath = null;
		// 파일이 있으면 수정하고, 없으면 수정하지 않는다.
		if(file != null) {
			// 1. 서버에 이미지를 업로드하고 imagePath를 받아온다.
			try {
				imagePath = fileManagerService.saveFile(userLoginId, file);
				
				// 2. 기존에 이미지가 있다면 파일을 제거한다.
				// -- 1번에서 업로드가 실패할 수 있으므로 성공 후에 제거
				if(post.getImagePath() != null && imagePath != null) {
					fileManagerService.deleteFile(post.getImagePath());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.error("[update.post] 파일 수정중 에러. postId:{}, error:{} ", postId, e.getMessage());
			}
			
			
			
			
		}
		
		// DB update
		
		return postDAO.updatePost(userId, postId, subject, content, imagePath);
	}
	
	
}
