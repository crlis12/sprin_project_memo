package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	// 실제 이미지가 저장되는 경로
	public final static String FILE_UPLOAD_PATH = "C:\\Users\\crlis\\OneDrive\\바탕 화면\\Spring_project\\spring_project_memo\\workspace\\images/";
	
	// input: file
	// output: image path
	public String saveFile(String loginId, MultipartFile file) throws IOException {
		// 파일 디렉토리 경로 예: crlis_1214124/sun.png 식으로 들어간다.
		// 파일명이 겹치지 않게 하기 위해 현재 시간을 경로에 붙여준다.
		String directoryName = loginId + "_" + System.currentTimeMillis() + "/";
		
		// C:\\Users\\crlis\\OneDrive\\바탕 화면\\Spring_project\\spring_project_memo\\workspace\\images/crlis_1214124/
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		if(directory.mkdir() == false) {
			return null; // 디렉토리 생성 실패시 path null 리턴
		}
		
		// 파일 업로드 : byte 단위로 업로드 한다.
		// 바이트 배열로 반환해준다
		byte[] bytes = file.getBytes();
		Path path = Paths.get(filePath + file.getOriginalFilename()); // getOriginalFilename:파일이름 그대로 넣는다
		Files.write(path, bytes); // 파일이 바이트 단위로 올라간다.
		
		// http://localhost/images/crlis_1214124/sun.png
		return "/images" + directoryName + file.getOriginalFilename();
	}
}
