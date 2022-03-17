package com.memo.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	// String
	@ResponseBody
	@RequestMapping("/test1")
	public String test1() {
		return "Hello World";
	}
	
	//json
	@ResponseBody
	@RequestMapping("/test2")
	public Map<String, Object> test2(){
		Map<String, Object> result = new HashMap<>();
		
		result.put("aa", 1234);
		result.put("bb", 12345);
		result.put("cc", 12346);
		result.put("dd", 12347);
		
		return result;
	}
	
	// jsp
	@RequestMapping("/test3")
	public String test3() {
		return "test/example";
	}
}
