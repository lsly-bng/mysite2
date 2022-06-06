<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.GuestbookVo"%>
<%@ page import="java.util.List"%>
<%@ page import="com.javaex.vo.UserVo"%>

<%
List<GuestbookVo> gbList = (List<GuestbookVo>) request.getAttribute("gbList");
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

					<div id="content-head" class="clearfix">
						<h3>일반방명록</h3>
						<div id="location">
							<ul>
								<li>홈</li>
								<li>방명록</li>
								<li class="last">일반방명록</li>
							</ul>
						</div>
					</div>
					<!-- //content-head -->

					<div id="guestbook">
						<form action="./guest" method="post">
							<table id="guestAdd">
								<colgroup>
									<col style="width: 70px;">
									<col>
									<col style="width: 70px;">
									<col>
								</colgroup>
								<tbody>
									<tr>
										<th><label class="form-text" for="input-uname">이름</label>
										</th>
										<td><input id="input-uname" type="text" name="name"
											value=""></td>
										<th><label class="form-text" for="input-pass">패스워드</label>
										</th>
										<td><input id="input-pass" type="password"
											name="password" value=""></td>
									</tr>
									<tr>
										<td colspan="4"><textarea name="content" cols="72"
												rows="5"></textarea></td>
									</tr>
									<tr class="button-area">
										<td colspan="4" class="text-center"><button type="submit">등록</button></td>
									</tr>
								</tbody>

							</table>
							<!-- //guestWrite -->
							<input type="hidden" name="action" value="add">
						</form>

						<%
						for (int i = 0; i < gbList.size(); i++) {
						%>
						<table class="guestRead">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 40%;">
								<col style="width: 10%;">
							</colgroup>
							<tr>
								<td><%=gbList.get(i).getNo()%></td>
								<td><%=gbList.get(i).getName()%></td>
								<td><%=gbList.get(i).getRegDate()%></td>
								<td><a
									href="./guest?action=deleteForm&no=<%=gbList.get(i).getNo()%>">[삭제]</a></td>
							</tr>
							<tr>
								<td colspan=4 class="text-left"><%=gbList.get(i).getContent()%></td>
							</tr>
						</table>
						<%
						}
						%>
						<!-- //guestRead -->

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