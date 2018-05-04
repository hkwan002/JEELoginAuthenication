package com.optimum.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.optimum.dao.UserDAO;
import com.optimum.dao.UserDAOImpl;
import com.optimum.pojo.User;


@WebServlet("/ImageController")
@MultipartConfig(maxFileSize = 16177215)
public class ImageController extends HttpServlet {

	private static final long serialVersionUID = 1L;

private UserDAO refUserDAO;
	
	User refUser = new User();
	public ImageController() {
		refUserDAO = new UserDAOImpl();
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		OutputStream img;
		System.out.println(email);
		// getting the image file and printing it to the screen 
		byte[] imgData = refUserDAO.getImage(email);
		response.setContentType("image/png");
		img = response.getOutputStream();
		img.write(imgData);
		img.flush();
		img.close();		
	}

}
