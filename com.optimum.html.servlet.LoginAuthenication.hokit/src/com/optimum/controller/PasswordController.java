package com.optimum.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.optimum.dao.UserDAO;
import com.optimum.dao.UserDAOImpl;
import com.optimum.pojo.User;


@WebServlet("/PasswordController")
public class PasswordController extends HttpServlet {
	private UserDAO refUserDAO;
	private User refUser;
	
	private static RequestDispatcher refRequestDispatcher;
	
    public PasswordController() {
        refUserDAO = new UserDAOImpl();
        refUser = new User();
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		refUser.setEmail(email);
		
		if(refUserDAO.checkEmail(refUser)) {
			refRequestDispatcher = request.getRequestDispatcher("/Login.jsp");
			refRequestDispatcher.include(request, response);
		}else {
			out.println("<html><body><font color='red'>Invalid email</font></body></html>");
			refRequestDispatcher = request.getRequestDispatcher("/Login.jsp");
			refRequestDispatcher.include(request, response);
		}
		
	}

}
