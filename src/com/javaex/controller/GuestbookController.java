package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guest")
public class GuestbookController extends HttpServlet {

	// field
	private static final long serialVersionUID = 1L;

	// constructor (use default)
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
		
		//dao
		GuestbookDao gbDao = new GuestbookDao();

		if ("add".equals(action)) {

			// 파라미터에서 값 꺼내기 (name, password, content)
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			// Vo만들어서 값 초기화
			GuestbookVo gbVo = new GuestbookVo(name, password, content);
			System.out.println(gbVo);

			// GuestbookDao.gbInsert()을 통해 추가하기
			gbDao.gbInsert(gbVo);

			// redirect
			WebUtil.redirect(request, response, "./guest");

		} else if ("deleteForm".equals(action)) {
			
			// deleteForm.jsp --> forward
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		} else if ("delete".equals(action)) {

			// 파라미터에서 값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			// Vo만들어서 값 초기화
			GuestbookVo gbVo = new GuestbookVo(no, password);

			// GuestbookDao.gbDelete()을 통해 삭제하기
			gbDao.gbDelete(gbVo);

			// redirect
			WebUtil.redirect(request, response, "./guest");

		} else {
			// 데이터 가져오기
			List<GuestbookVo> gbList = gbDao.getGbList();

			// request에 데이터 추가
			request.setAttribute("gbList", gbList);

			// 데이터+html --> jsp 시킨다
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		}
	}

	// post 방식으로 요청시 호출하는 메소드
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
