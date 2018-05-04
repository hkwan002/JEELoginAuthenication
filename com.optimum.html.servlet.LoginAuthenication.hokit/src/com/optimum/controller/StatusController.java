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


@WebServlet("/StatusController")
public class StatusController extends HttpServlet {
	
	private UserDAO refUserDAO;
	private User refUser;
	
	private RequestDispatcher refRequestDispatcher;
	
    public StatusController() {
        refUserDAO = new UserDAOImpl();
        refUser = new User();
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		
		String employeeID = request.getParameter("employeeID");
		refUser.setEmployeeID(employeeID);
		
		if (refUserDAO.checkUser(refUser) && refUserDAO.changeStatus(refUser)){
			out.println("<html><body><font color='red'>Account is lock</font></body></html>");
			refRequestDispatcher = request.getRequestDispatcher("/ChangeStatus.jsp");
			refRequestDispatcher.include(request, response);
		} else {
			out.println("<html><body><font color='red'>Account is unlock</font></body></html>");
			refRequestDispatcher = request.getRequestDispatcher("/ChangeStatus.jsp");
			refRequestDispatcher.include(request, response);
		}
			
			
		
	}

}
