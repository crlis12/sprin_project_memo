<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 
<div class="d-flex justify-content-between">
	<div class="logo">
		<div class="logo_font font-weight-bold">메모 게시판</div>
	</div>
	<div class="login-info d-flex">
		<c:if test="${not empty userName}">
		<div class="mt-5 mr-4" >
			<span class="text-white">${userName }님 안녕하세요</span>
			<a href="/user/sign_out" class="ml-2 text-white font-weight-bold">로그아웃</a>
		</div>
		</c:if>
	</div>
</div>