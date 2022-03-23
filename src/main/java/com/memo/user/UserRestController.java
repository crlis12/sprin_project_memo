package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.model.User;

	@RequestMapping("/user")
	@RestController // @ResponseBody + @Controller: 데이터로 내려준다.
	public class UserRestController {
		
		@Autowired
		private UserBO userBO;
		
		/**
		 * ID 중복 확인
		 * @param loginId
		 * @return
		 */
		@RequestMapping("/is_duplicated_id")
		public Map<String, Object> isDuplicatedId(
				@RequestParam("loginId") String loginId) {
			
			Map<String, Object> result = new HashMap<>();
			int existRowCount = userBO.existLoginId(loginId);
			if (existRowCount > 0) { // 이미 id가 존재하면 true
				result.put("result", true);
			} else {
				result.put("result", false);
			}
			
			return result;
		}
		
		@RequestMapping("/sign_up")
		public Map<String, Object> signUpForAjax(
				@RequestParam("loginId") String loginId,
				@RequestParam("password") String password,
				@RequestParam("name") String name,
				@RequestParam("email") String email) {
			
			String encryptPassword = EncryptUtils.md5(password);
			int row = userBO.addUser(loginId, encryptPassword, name, email);
			
			Map<String, Object> result = new HashMap<>();
			
			if (row == 1) {
				result.put("result", "success"); 
			} else { 
				result.put("error", "입력 실패"); 
			}
			 
			return result;
		}
		
		@PostMapping("/sign_in")
		public Map<String, Object> signIn(
				@RequestParam("loginId") String loginId,
				@RequestParam("password") String password,
				HttpServletRequest request ) {
			
			// password 해싱(암호화)
			String encrytUtils = EncryptUtils.md5(password);
			// DB login, 해싱된 password 셀렉트
			User user = userBO.getUserByLoginIdAndPassword(loginId, encrytUtils);
			
			Map<String, Object> result = new HashMap<String, Object>();
			if(user != null) {
				// 결과가 있으면 로그인 처리
				result.put("result", "success");
				
				// 세션에 로그인 정보 저장(로그인 상태를 유지)
				HttpSession session = request.getSession();
				session.setAttribute("userId", user.getId());
				session.setAttribute("userName", user.getName());
				session.setAttribute("userLoginId", user.getLoginId());
			} else {
				// 결과가 없으면 에러 처리
				result.put("result", "error");
				result.put("error_message", "존재하지 않는 사용자 입니다. ");
				
			}
			
			//return
			return result;
		}
}
