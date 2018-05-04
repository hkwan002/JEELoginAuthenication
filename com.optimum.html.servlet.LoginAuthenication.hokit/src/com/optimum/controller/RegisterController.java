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

import com.optimum.dao.RegisterDAO;
import com.optimum.dao.RegisterDAOImpl;
import com.optimum.pojo.User;


@WebServlet("/RegisterController")
@MultipartConfig(maxFileSize = 16177215) 
public class RegisterController extends HttpServlet {
	private RegisterDAO refRegisterDAO;
	private User refUser;
	
	private static RequestDispatcher refRequestDispatcher;
	
    public RegisterController() {
        refRegisterDAO = new RegisterDAOImpl();
        refUser = new User();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		InputStream inputStream = null;
		
		//get data from html page
		
		String name = request.getParameter("uname");
	
		String nric = request.getParameter("ic");

		String gender = request.getParameter("gender");

		String dob = request.getParameter("dob");

		String address = request.getParameter("address");

		String country = request.getParameter("countries");

		String qualification = request.getParameter("qualification");
		
		Part filePart = request.getPart("attachments");
		 if (filePart != null) {
			 inputStream = filePart.getInputStream();
		 }
		String department = request.getParameter("department");

		String email = request.getParameter("email");

		String mobile = request.getParameter("mobile");

		String empID = request.getParameter("empID");

		
		//set data to POJO class
		refUser.setName(name);
		refUser.setNric(nric);
		refUser.setGender(gender);
		refUser.setDob(dob);
		refUser.setAddress(address);
		refUser.setCountry(country);
		refUser.setQualification(qualification);
		refUser.setDepartment(department);
		refUser.setEmail(email);
		refUser.setMobile(mobile);
		refUser.setEmployeeID(empID);
		
		String tempPassword = nric.substring(1,5) + mobile.substring(4,8);
		refUser.setUserPassword(tempPassword);

		
	if (refRegisterDAO.newUser(refUser, inputStream, tempPassword)) {
			out.println("<html><body><font color='red'>New user created</font></body></html>");
			refRequestDispatcher = request.getRequestDispatcher("/NewRegister.jsp");
			refRequestDispatcher.include(request, response);
	}else
		out.println("<html><body><font color='red'>Unable to create new user</font></body></html>");
	refRequestDispatcher = request.getRequestDispatcher("/NewRegister.jsp");
	refRequestDispatcher.include(request, response);
	}

}
