package com.optimum.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.optimum.connection.DatabaseUtility;
import com.optimum.pojo.User;

public class RegisterDAOImpl implements RegisterDAO {
	private static Connection conn = DatabaseUtility.getConnection();
	
	private boolean addUser;

	static Logger log4j = Logger.getLogger(UserDAOImpl.class);
	
	@Override
	public boolean newUser(User refUser, InputStream inputStream, String tempPassword) {
		try {	// Create new user
			String query = "INSERT into USER (name, nric, gender, dob, address, country, qualification, certificate, department, email, mobile, employeeID, password, firstlogin, status, noofattempts) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getName());
			preparedStatement.setString(2, refUser.getNric());				
			preparedStatement.setString(3, refUser.getGender());
			preparedStatement.setString(4, refUser.getDob());
			preparedStatement.setString(5, refUser.getAddress());
			preparedStatement.setString(6, refUser.getCountry());
			preparedStatement.setString(7, refUser.getQualification());
			preparedStatement.setBlob(8, inputStream);
			preparedStatement.setString(9, refUser.getDepartment());
			preparedStatement.setString(10, refUser.getEmail());
			preparedStatement.setString(11, refUser.getMobile());
			preparedStatement.setString(12, refUser.getEmployeeID());
			preparedStatement.setString(13, refUser.getUserPassword());
			preparedStatement.setBoolean(14, true);
			preparedStatement.setBoolean(15, true);
			preparedStatement.setInt(16, 0);
			int count = preparedStatement.executeUpdate();
		
			if(count>0) {
				addUser =true;
				sendEmail(refUser, tempPassword);
				log4j.info("New employee has been registered.");
			}else {
				addUser=false;
				log4j.error("Account cannot be created.");
			}		
			preparedStatement.close();
		}
		
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			return addUser;
		}
	public void sendEmail(User refUser, String tempPassword) {
		
		String to = refUser.getEmail();
		String from = "optimum.batch5@gmail.com";
		String emailPass = "Optimum2018";
		
		//Getting session object
			Properties props = System.getProperties();
				props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
				props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
				props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
				props.put("mail.smtp.port", "465"); // SMTP Port
				
				Authenticator auth = new Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from,emailPass);
			}
		};
		Session session = Session.getDefaultInstance(props, auth); 
			//compose the message
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			msg.setSubject("Your password");
			msg.setText("Hello " + refUser.getName() + "! The following is your password: " + tempPassword);
			
			//Send message
			Transport.send(msg);
			//System.out.println("Temporary password has been sent to " + refUser.getEmail() + ".");
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
