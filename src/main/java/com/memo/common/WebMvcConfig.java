package com.memo.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //mapping 설정 필수
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)	{
		registry
		.addResourceHandler("/images/**") // http://localhost/images/crlis_1214124/sun.png 와 같이 접근 가능하게 매핑해준다
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);	// 윈도우 기준 /:3개 맥은/:1개 실제 파일저장위치
		
	}
}
