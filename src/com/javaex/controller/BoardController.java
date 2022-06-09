package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	// field
	private static final long serialVersionUID = 1L;

	// constructor
	// method - g/s
	// method - general

	// get 방식으로 요청시 호출하는 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// action파라미터 꺼내기
		String action = request.getParameter("action");
		System.out.println(action);

		// dao
		BoardDao brdDao = new BoardDao();

		if ("read".equals(action)) {

			// 파라미터에서 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));

			// Vo에 dao 내용 담기
			BoardVo bVo = brdDao.brdRead(no);
			System.out.println(bVo);

			// dao 조회수 메소드 사용
			brdDao.updateHit(no);

			// Attribute에 Vo 담기
			request.setAttribute("bVo", bVo);

			// forward
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("writeForm".equals(action)) {

			// 세션 확인 및 정의하기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			// 로그인 정보 확인하기
			if (authUser == null) {
				// 세션 없으면 로그인 페이지로 redirect 하기
				WebUtil.redirect(request, response, "./user?action=loginForm");
			} else {
				// 세션 있으면 글씨기폼으로 forward 하기
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			}

		} else if ("write".equals(action)) {

			// 파라미터에서 값 꺼내기
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// 세션 확인 및 정의하기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			// Vo 만들기
			BoardVo bVo = new BoardVo();
			bVo.setTitle(title);
			bVo.setContent(content);
			bVo.setUserNo(authUser.getNo());

			// dao에서 write 메소드 사용하기
			brdDao.brdWrite(bVo);

			// redirect
			WebUtil.redirect(request, response, "./board");

		} else if ("delete".equals(action)) {

			// 파라미터에서 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));

			// Vo에 dao 내용 담기
			brdDao.brdDelete(no);

			// redirect
			WebUtil.redirect(request, response, "./board");

		} else if ("modifyForm".equals(action)) {

			// 파라미터에서 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));

			// vo dao
			BoardVo bVo = brdDao.brdRead(no);

			// request data
			request.setAttribute("bVo", bVo);

			// forward
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		} else if ("modify".equals(action)) {

			// 파라미터에서 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// 묶어준다
			BoardVo bVo = new BoardVo(title, content, no);

			// dao에서 modify메소드 사용하기
			brdDao.brdModify(bVo);

			// 리다이렉트(main)
			WebUtil.redirect(request, response, "./board");

		} else {

			// 리스트 데이터 가져오기
			List<BoardVo> brdList = brdDao.getBoardList();

			// 파라미터에서 값 꺼내기
			request.getParameter("keyword");

			// Attribute 만들기
			request.setAttribute("brdList", brdList);

			// 데이터+html --> jsp 시킨다
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	// post 방식으로 요청시 호출하는 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
