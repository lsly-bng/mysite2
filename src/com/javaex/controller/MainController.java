package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;

@WebServlet("/main")
public class MainController extends HttpServlet {

	// field
	private static final long serialVersionUID = 1L;

	// constructor
	// method - g/s
	// method - general
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 포스트 방식일때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// index.jsp 로 forward
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
