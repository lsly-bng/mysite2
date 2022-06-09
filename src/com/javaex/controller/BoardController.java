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

		// action 파라미터 꺼내기
		String action = request.getParameter("action");
		System.out.println(action);

		// dao
		BoardDao brdDao = new BoardDao();
		
		// vo
		BoardVo bVo = new BoardVo();

		// 게시글 읽기 -> 전체 사용자 (회원 비회원)
		if ("read".equals(action)) {

			// parameter 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));

			// dao 조회수 메소드 사용
			brdDao.updateHit(no);

			// dao read 메소드 사용
			bVo = brdDao.brdRead(no);
			System.out.println(bVo);

			// request에 데이터 추가
			request.setAttribute("bVo", bVo);

			// forward 메소드 -> read.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		// 글쓰기폼 불러오기 -> session 있으면 -> writeForm.jsp forward
		} else if ("writeForm".equals(action)) {

			// session 확인 및 데이터 불러오기
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

		// 게시글 등록하기 -> 회원 한정
		} else if ("write".equals(action)) {

			// parameter 값 꺼내기
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// session 확인 및 데이터 불러오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			// vo 만들기 -> 값 초기화
			bVo = new BoardVo(title, content);
			bVo.setUserNo(authUser.getNo()); // 확인필요

			// dao write 메소드 사용
			brdDao.brdWrite(bVo);

			// redirect -> ./board
			WebUtil.redirect(request, response, "./board");

		// 게시글 삭제 -> 작성자 한정
		} else if ("delete".equals(action)) {

			// parameter 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));

			// dao delete 메소드 사용
			brdDao.brdDelete(no);

			// redirect -> ./board
			WebUtil.redirect(request, response, "./board");

		} else if ("modifyForm".equals(action)) {

			// parameter 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));

			// dao read 메소드 사용
			bVo = brdDao.brdRead(no);

			// request에 데이터 추가
			request.setAttribute("bVo", bVo);

			// forward -> modifyForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		// 게시글 수정 -> 작성자 한정
		} else if ("modify".equals(action)) {
			
			//  parameter 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// vo 만들기 -> 값 초기화
			bVo = new BoardVo(title, content, no);

			// dao modify 메소드 사용
			brdDao.brdModify(bVo);

			// redirect -> ./board
			WebUtil.redirect(request, response, "./board");

		// default -> list
		} else {

			// list data dao에서 가져오기
			List<BoardVo> brdList = brdDao.getBoardList();

			// parameter 값 꺼내기 
			request.getParameter("keyword");

			// request에 데이터 추가
			request.setAttribute("brdList", brdList);

			// forward -> list.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	// post 방식으로 요청시 호출하는 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
