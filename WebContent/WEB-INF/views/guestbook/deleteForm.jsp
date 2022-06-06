<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.UserVo"%>


<%
int no = Integer.parseInt(request.getParameter("no"));
UserVo authUser = (UserVo) session.getAttribute("authUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="/mysite2/assets/css/mysite.css" rel="stylesheet"
	type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">

</head>

<body>
	<div id="wrap">

		<div id="header" class="clearfix">
			<h1>
				<a href="./main">MySite</a>
			</h1>

			<%
			if (authUser == null) {
			%>
			<!-- 로그인 실패, 로그인전 -->
			<ul>
				<li><a href="./user?action=loginForm" class="btn_s">로그인</a></li>
				<li><a href="./user?action=joinForm" class="btn_s">회원가입</a></li>
			</ul>
			<%
			} else {
			%>
			<!-- 로그인 성공 -->
			<ul>
				<li><%=authUser.getName()%> 님 안녕하세요 :)</li>
				<li><a href="./user?action=logout" class="btn_s">로그아웃</a></li>
				<li><a href="./user?action=modifyForm" class="btn_s">회원정보수정</a></li>
			</ul>
			<%
			}
			%>

			<div id="nav">
				<ul class="clearfix">
					<li><a href="">입사지원서</a></li>
					<li><a href="">게시판</a></li>
					<li><a href="">갤러리</a></li>
					<li><a href="./guest">방명록</a></li>
				</ul>
			</div>
			<!-- //nav -->

			<div id="container" class="clearfix">
				<div id="aside">
					<h2>방명록</h2>
					<ul>
						<li>일반방명록</li>
						<li>ajax방명록</li>
					</ul>
				</div>
				<!-- //aside -->

				<div id="content">

					<div id="content-head">
						<h3>일반방명록</h3>
						<div id="location">
							<ul>
								<li>홈</li>
								<li>방명록</li>
								<li class="last">일반방명록</li>
							</ul>
						</div>
						<div class="clear"></div>
					</div>
					<!-- //content-head -->

					<div id="guestbook">
						<form action="./guest" method="get">
							<table id="guestDelete">
								<colgroup>
									<col style="width: 10%;">
									<col style="width: 40%;">
									<col style="width: 25%;">
									<col style="width: 25%;">
								</colgroup>
								<tr>
									<td>비밀번호</td>
									<td><input type="password" name="password" value=""></td>
									<td class="text-left"><button type="submit">삭제</button></td>
									<td><a href="./guest">[메인으로 돌아가기]</a></td>
								</tr>
							</table>
							<input type='hidden' name="no" value="<%=no%>"> <input
								type='hidden' name="action" value="delete">
						</form>

					</div>
					<!-- //guestbook -->
				</div>
				<!-- //content  -->

			</div>
			<!-- //container  -->

			<div id="footer">Copyright ⓒ 2022 KWAKWT. All right reserved.</div>
			<!-- //footer -->

		</div>
	</div>
	<!-- //wrap -->

</body>

</html>