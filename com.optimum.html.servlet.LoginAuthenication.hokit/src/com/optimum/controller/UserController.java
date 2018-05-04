package com.optimum.controller;

import java.awt.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.optimum.dao.UserDAO;
import com.optimum.dao.UserDAOImpl;
import com.optimum.pojo.User;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

	private UserDAO refUserDAO;
	private User refUser;
	
	private RequestDispatcher refRequestDispatcher;
	
    public UserController() {
        refUserDAO = new UserDAOImpl();
        refUser = new User();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//get data from html page
		
		String Username = request.getParameter("uname");
		String password = request.getParameter("pwd");
		
		//set data to POJO class
	
		refUser.setEmail(Username);
		refUser.setUserPassword(password);
		
		if (Username.isEmpty()) {
			request.setAttribute("Uname","<html><body><font color='red'>User name can't be null</font></body></html>"); 
			getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
		}
		else if (password.isEmpty()) {
			request.setAttribute("Password","<html><body><font color='red'>Password can't be null</font></body></html>"); 
			getServletContext().getRequestDispatcher("/Login.jsp").forward(request,response);
		} 
		else if (refUserDAO.loginAuthentication(refUser) && refUserDAO.adminCheck(refUser)) {		// true
			User refUser = new User();
			session.setAttribute("refUser", Username);
			session.setAttribute("refUser", password);
			session.setMaxInactiveInterval(10*60);
			Date date = new Date(session.getLastAccessedTime());
			session.setAttribute("lastAccessTime", date);
			session.setAttribute("name", refUser.getName());
			String encode = response.encodeRedirectURL("AdminPage.jsp");
			response.sendRedirect(encode);
		
		}

		else if (refUserDAO.loginAuthentication(refUser) && refUserDAO.checkStatus(refUser)){
			refUserDAO.resetattempts(refUser);
			session.setAttribute("refUser", Username);
			session.setAttribute("refUser", password);
			session.setMaxInactiveInterval(10*60);
			Date lastAccessTime = new Date(session.getLastAccessedTime());	//Get last access time
			session.setAttribute("lastAccessTime", lastAccessTime);
			request.setAttribute("name", refUser.getName()); 
			
			session.setAttribute("refEmail", refUser.getEmail());
			session.setAttribute("currentUser", refUser);
			
			refRequestDispatcher = request.getRequestDispatcher("/User.jsp");
			refRequestDispatcher.include(request, response);
		} 
		else if (refUserDAO.noofattempts(refUser)){
			out.println("<html><body><font color='red'>Invalid Login or password</font></body></html>");
			refRequestDispatcher = request.getRequestDispatcher("/Login.jsp");
			refRequestDispatcher.include(request, response);
		} else {
			out.println("<html><body><font color='red'>Your account have been lock.</font></body></html>");
			refRequestDispatcher = request.getRequestDispatcher("/Login.jsp");
			refRequestDispatcher.include(request, response);
		}
		
	}
}
