package com.memo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.interceptor.PermissionInterceptor;

@Configuration //mapping 설정 필수
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private PermissionInterceptor interceptor;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)	{
		registry
		.addResourceHandler("/images/**") // http://localhost/images/crlis_1214124/sun.png 와 같이 접근 가능하게 매핑해준다
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);	// 윈도우 기준 /:3개 맥은/:1개 실제 파일저장위치
		
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)	{
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**") //** 아래 디렉토리까지 모두 확인
		.excludePathPatterns("/error", "static/**", "/user/sign_out"); // 예외처리 (인터셉터 안타게)
	}
}
