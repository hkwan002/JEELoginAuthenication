package com.optimum.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.optimum.dao.UserDAO;
import com.optimum.dao.UserDAOImpl;
import com.optimum.pojo.User;


@WebServlet("/UpdateController")
@MultipartConfig(maxFileSize = 16177215)
public class UpdateController extends HttpServlet {
	private UserDAO refUserDAO;
	private User refUser;
	
	private static RequestDispatcher refRequestDispatcher;
	
    public UpdateController() {
        refUserDAO = new UserDAOImpl();
        refUser = new User();
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		InputStream inputStream = null, inputStream2 = null;
		
		Part profile = request.getPart("ProfilePic");
		 if (profile != null) {
			 inputStream = profile.getInputStream();
		 }
		 String email = request.getParameter("email");
		 
		 String address = request.getParameter("address");
		 
		 String qualification = request.getParameter("qualification");
		 
		 Part certificate = request.getPart("attachments");
		 if (certificate != null) {
			 inputStream2 = certificate.getInputStream();
		 }
		 
		 String mobile = request.getParameter("mobile");
		 
		 refUser.setAddress(address);
		 refUser.setQualification(qualification);
		 refUser.setMobile(mobile);
		 refUser.setEmail(email);
		
		 
		 if (refUserDAO.updateUser(refUser, inputStream, inputStream2)) {
			 out.println("<html><body><font color='red'>Account have been updated.</font></body></html>");
				refRequestDispatcher = request.getRequestDispatcher("/EditUser.jsp");
				refRequestDispatcher.include(request, response);
		 } else {
			 out.println("<html><body><font color='red'>Unable to update.</font></body></html>");
		 }
	}

}
