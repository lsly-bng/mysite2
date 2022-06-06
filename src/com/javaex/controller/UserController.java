package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	// field
	private static final long serialVersionUID = 1L;
	// constructor
	// method - g/s
	// method - general

	// doGet METHOD
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// action 파라미터 꺼내기
		String action = request.getParameter("action");

		if ("joinForm".equals(action)) {// 회원가입 폼
			// 확인용
			System.out.println("UserController>joinForm");

			// forward --> joinForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {// 회원가입
			// 확인용
			System.out.println("UserController>join");

			// 파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// Vo 만들기
			UserVo userVo = new UserVo(id, name, password, gender);
			System.out.println(userVo);

			// Dao를 이용해서 저장하기
			UserDao userDao = new UserDao();
			userDao.insert(userVo);

			// forward --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) {// 로그인 폼
			// 확인용
			System.out.println("UserController>loginForm");

			// forward --> loginForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {// 로그인
			// 확인용
			System.out.println("UserController>login");

			// 파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");

			// Vo 만들기
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);

			// Dao를 이용해서 저장하기
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.getUser(userVo); // id, password --> user 전체

			// authUser 주소값이 있으면 --> 로그인 성공
			// authUser null이면 --> 로그인 실패
			if (authUser == null) {
				System.out.println("로그인 실패");
			} else {
				System.out.println("로그인 성공");

				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				// 메인 리다이렉트
				WebUtil.redirect(request, response, "./main");
			}
		} else if ("logout".equals(action)) {
			// 확인용
			System.out.println("UserController>logout");

			// *세션값을 지운다
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();

			// 메인으로 리다이렉트
			WebUtil.redirect(request, response, "./main");

		} else if ("modifyForm".equals(action)) { // 수정폼

			System.out.println("UserController>modifyForm");

			// 로그인한 사용자의 no 값을 세션에서 가져오기		
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			int no = authUser.getNo(); // 세션 별로 회원정보 제한 조치

			if (authUser != null) {
				// no 로 사용자 정보 가져오기
				UserDao userDao = new UserDao();
				UserVo userVo = userDao.getUser(no); // no id password name gender

				// request 의 attribute 에 userVo 는 넣어서 포워딩
				request.setAttribute("userVo", userVo);

				// modifyForm.jsp forward
				WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			}

		} else if ("modify".equals(action)) { // 수정

			System.out.println("UserController>modify");

			// 세션에서 no
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser"); // attribute 는 저장창고이기 때문에 형변환을 꼭 해줘야함
			int no = authUser.getNo(); // 세션에 있는 no 불러오기 --> 회원정보 제한 조치

			// 파라미터꺼낸다
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// 묶어준다
			UserVo userVo = new UserVo(no, name, password, gender);

			// dao를 사용한다
			UserDao userDao = new UserDao();
			userDao.update(userVo);

			// 세션 값 리셋
			session.setAttribute("authUser", userVo);
			((UserVo)session.getAttribute("authUser")).setName(name);

			// 리다이렉트(main)
			WebUtil.redirect(request, response, "./main");
		}
	}

	// doPost METHOD
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
