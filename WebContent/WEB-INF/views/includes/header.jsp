<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header" class="clearfix">
	<h1>
		<a href="./main">MySite</a>
	</h1>

	<c:choose>
		<c:when test="${authUser != null}">
			<ul>
				<li><span>${authUser.name}</span> 님 안녕하세요^^</li>
				<li><a href="./user?action=logout" class="btn_s">로그아웃</a></li>
				<li><a href="./user?action=modifyForm" class="btn_s">회원정보수정</a></li>
			</ul>
		</c:when>

		<c:otherwise>
			<ul>
				<li><a href="./user?action=loginForm" class="btn_s">로그인</a></li>
				<li><a href="./user?action=joinForm" class="btn_s">회원가입</a></li>
			</ul>
		</c:otherwise>
	</c:choose>

</div>
<!-- //header -->

<div id="nav">
	<ul class="clearfix">
		<li><a href="">입사지원서</a></li>
		<li><a href="./board">게시판</a></li>
		<li><a href="">갤러리</a></li>
		<li><a href="./guest">방명록</a></li>
	</ul>
</div>
<!-- //nav -->
