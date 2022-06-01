package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {

	// field
	// constructor
	// method - g/s
	// method - general

	// forward method
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	// redirect method
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws ServletException, IOException {

		response.sendRedirect(url);
	}
}
