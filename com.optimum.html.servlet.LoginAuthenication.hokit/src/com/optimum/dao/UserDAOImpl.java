package com.optimum.dao;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class UserDAOImpl implements UserDAO{
	
	private static Connection conn = DatabaseUtility.getConnection();
	
	static Logger log4j = Logger.getLogger(UserDAOImpl.class);
	
	private boolean loginStatus;
	private boolean adminCheck;
	private boolean firstLogin;
	private boolean checkStatus;
	private boolean checkUser;
	private boolean changeStatus;
	private boolean checkEmail;
	private boolean noofattempts;
	private boolean updateUser;

	@Override
	public boolean loginAuthentication(User refUser) {
		try {		// check if email and password is correct 
			String query = "SELECT * FROM USER WHERE email =? and password=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getEmail());
			preparedStatement.setString(2, refUser.getUserPassword());
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {	// when there is output
				loginStatus = true;
				refUser.setDepartment(rs.getString("department"));
				refUser.setFirstlogin(rs.getBoolean("firstlogin"));
				refUser.setStatus(rs.getString("status"));
				refUser.setName(rs.getString("name"));
				refUser.setEmployeeID(rs.getString("employeeID"));
				refUser.setMobile(rs.getString("mobile"));
				refUser.setAddress(rs.getString("address"));
				refUser.setQualification(rs.getString("qualification"));
				refUser.setEmail(rs.getString("email"));
				refUser.setImage(rs.getBlob("profilepic"));
				refUser.setProfilepic(rs.getBinaryStream("profilepic"));
				refUser.setCertificate(rs.getBinaryStream("certificate"));

			} else {
				loginStatus = false;
				
				try {		// check if email is correct 
					String query2 = "SELECT * FROM USER WHERE email =?";
					PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
					preparedStatement2.setString(1, refUser.getEmail());
					ResultSet rs2 = preparedStatement.executeQuery();
					if (rs2.next()) {
						refUser.setNoofattempts(rs2.getInt("noofattempts"));
					} else {
						log4j.warn("Invalid email input");
					}
				}catch(SQLException e) {
						e.printStackTrace();
					}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			log4j.error("Unable to execute loginAuthenticate.");
		}
		return loginStatus;
	}

	@Override	// check for admin which bypass attempts and status
	public boolean adminCheck(User refUser) {
		if (refUser.getDepartment().equalsIgnoreCase("admin")) {
			adminCheck = true;
		log4j.info("admin login");
		}
		else
			adminCheck = false;
		return adminCheck;	
	}

	@Override
	public boolean firstLogin(User refUser) {
		if(refUser.isFirstlogin())
			firstLogin=true;
		else
			firstLogin=false;
		return firstLogin;
	}

	@Override	// check status 
	public boolean checkStatus(User refUser) {
		if (refUser.getStatus().equals("unlock")) {
			checkStatus=true;
			log4j.info("user successful login");
		}else 
			checkStatus=false;
		return checkStatus;
	}
	public void resetattempts(User refUser) {
		try {	//reset attempts after successful lock in
			String query2 = "UPDATE USER set noofattempts = ? where email =? ";
			PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
			preparedStatement2.setInt(1, 0);
			preparedStatement2.setString(2, refUser.getEmail());
			preparedStatement2.executeUpdate();
			preparedStatement2.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkUser(User refUser) {
		try {		// check employeeID is valid
			String query = "SELECT * FROM USER WHERE employeeID =?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getEmployeeID());
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {	// when there is output
				refUser.setStatus(rs.getString("status"));
				checkUser = true;	
			} else {
				checkUser = false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			log4j.error("Unable to find employeeID.");
		}
		return checkUser;
	}

	@Override
	public boolean changeStatus(User refUser) {
		if (refUser.getStatus().equals("unlock")) {
			try {		// change status accordingly (lock to unlock or unlock to lock)
				String query2 = "UPDATE USER set status = ? where employeeID =? ";
				PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
				preparedStatement2.setString(1, "lock");
				preparedStatement2.setString(2, refUser.getEmployeeID());
				int rowcount = preparedStatement2.executeUpdate();
				if (rowcount > 0) {
					changeStatus = true;
				}
			}catch(SQLException e) {
				e.printStackTrace();
				log4j.error("Unable to change status.");
			}
		} else {
			try {
				String query3 = "UPDATE USER set status = ? where employeeID =? ";
				PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
				preparedStatement3.setString(1, "unlock");
				preparedStatement3.setString(2, refUser.getEmployeeID());
				int rowcount = preparedStatement3.executeUpdate();
				if (rowcount > 0) {
					changeStatus = false;
				}
			}catch(SQLException e) {
				e.printStackTrace();
				log4j.error("Unable to change status.");
			}
		}
		return changeStatus;
	}

	@Override
	public boolean checkEmail(User refUser) {
		try {	// check email is valid
			String query4="Select * from user where email = \'"+refUser.getEmail()+"\'";
			PreparedStatement preparedStatement4 = conn.prepareStatement(query4);
			ResultSet rs2 = preparedStatement4.executeQuery();
			
			if(rs2.next()) {
			refUser.setName(rs2.getString("name"));
			String nric = rs2.getString("nric");
			String mobile = rs2.getString("mobile");
			String tempPassword = nric.substring(1,5) + mobile.substring(4,8);
			refUser.setUserPassword(tempPassword);
			checkEmail=true;
			try {	// reset password
				String query5 = "UPDATE USER set password = ? where email =? ";
				PreparedStatement preparedStatement5 = conn.prepareStatement(query5);
				preparedStatement5.setString(1,refUser.getUserPassword());
				preparedStatement5.setString(2, refUser.getEmail());
				preparedStatement5.executeUpdate();
				
				}catch(SQLException e) {
				e.printStackTrace();
				log4j.error("Unable to reset email.");
				}
			sendEmail(refUser, tempPassword);
			} else
				checkEmail=false;
		}catch (Exception e) {
				e.printStackTrace();
				log4j.error("Unable to find user.");
				}
		return checkEmail;

		}

		
		public void sendEmail(User refUser, String tempPassword) {
			// send reset password to email 
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
				
			}catch(Exception e) {
				System.out.println(e);
			}

		} // End of Send Temp password to User

		@Override
		public boolean noofattempts(User refUser) {
			if (refUser.getNoofattempts()<2) {
				// increase attempts by 1
				try {
					String query2 = "UPDATE USER set noofattempts = ? where email =? ";
					PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
					refUser.setNoofattempts(refUser.getNoofattempts()+1);
					preparedStatement2.setInt(1, refUser.getNoofattempts());
					preparedStatement2.setString(2, refUser.getEmail());
					int rowcount = preparedStatement2.executeUpdate();
					if (rowcount > 0) {
						noofattempts = true;
					}
				}catch(SQLException e) {
					e.printStackTrace();
					log4j.error("Unable to update attempts.");
				}
			}else {
				try {	// lock account
					String query3 = "UPDATE USER set status = ? where email =? ";
					PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
					preparedStatement3.setString(1, "lock");
					preparedStatement3.setString(2, refUser.getEmail());
					int rowcount = preparedStatement3.executeUpdate();
					if (rowcount > 0) {
						noofattempts = false;
					}
				}catch(SQLException e) {
					e.printStackTrace();
					log4j.error("Unable to change status.");
				}
			}
				
			return noofattempts;
			
		}

		@Override
		public boolean updateUser(User refUser, InputStream inputStream, InputStream inputStream2) {
			if(inputStream==null) {
				try {	// update user profile
					String query3 = "UPDATE USER set address=?, qualification=?, certificate=?, mobile=? where email=? ";
					PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
					
					preparedStatement3.setString(1, refUser.getAddress());
					preparedStatement3.setString(2, refUser.getQualification());
					preparedStatement3.setBlob(3, inputStream2);
					preparedStatement3.setString(4, refUser.getMobile());
					preparedStatement3.setString(5, refUser.getEmail());
					int rowcount = preparedStatement3.executeUpdate();
					if (rowcount > 0) {
						updateUser = true;
					}
				}catch(SQLException e) {
					e.printStackTrace();
					log4j.error("Unable to update user profile.");
				}
			} else if(inputStream2==null) {
				try {	// update user profile
					String query3 = "UPDATE USER set profilepic =?, address=?, qualification=?, mobile=? where email=? ";
					PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
					
					preparedStatement3.setBlob(1, inputStream);
					preparedStatement3.setString(2, refUser.getAddress());
					preparedStatement3.setString(3, refUser.getQualification());
					preparedStatement3.setString(4, refUser.getMobile());
					preparedStatement3.setString(5, refUser.getEmail());
					int rowcount = preparedStatement3.executeUpdate();
					if (rowcount > 0) {
						updateUser = true;
					}
				}catch(SQLException e) {
					e.printStackTrace();
					log4j.error("Unable to update user profile.");
				}
			}else if(inputStream==null & inputStream2==null) {
				try {	// update user profile
					String query3 = "UPDATE USER set address=?, qualification=?, mobile=? where email=? ";
					PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
					
					preparedStatement3.setString(1, refUser.getAddress());
					preparedStatement3.setString(2, refUser.getQualification());
					preparedStatement3.setString(3, refUser.getMobile());
					preparedStatement3.setString(4, refUser.getEmail());
					int rowcount = preparedStatement3.executeUpdate();
					if (rowcount > 0) {
						updateUser = true;
					}
				}catch(SQLException e) {
					e.printStackTrace();
					log4j.error("Unable to update user profile.");
				}
			} else {
				try {	// update user profile
					String query3 = "UPDATE USER set profilepic =?, address=?, qualification=?, certificate=?, mobile=? where email=? ";
					PreparedStatement preparedStatement3 = conn.prepareStatement(query3);
					
					preparedStatement3.setBlob(1, inputStream);
					preparedStatement3.setString(2, refUser.getAddress());
					preparedStatement3.setString(3, refUser.getQualification());
					preparedStatement3.setBlob(4, inputStream2);
					preparedStatement3.setString(5, refUser.getMobile());
					preparedStatement3.setString(6, refUser.getEmail());
					int rowcount = preparedStatement3.executeUpdate();
					if (rowcount > 0) {
						updateUser = true;
					}
				}catch(SQLException e) {
					e.printStackTrace();
					log4j.error("Unable to update user profile.");
				}
			}
				return updateUser;
			}
			

		@Override
		public String hashPassword(String userPassword) {
				
		        String passwordToHash = userPassword;
		        String generatedPassword = null;
		        try {
		            // Create MessageDigest instance for MD5
		            MessageDigest md = MessageDigest.getInstance("MD5");
		            
		            //Add password bytes to digest
		            md.update(passwordToHash.getBytes());
		            
		            //Get the hash's bytes
		            byte[] bytes = md.digest();
		            
		            //This bytes[] has bytes in decimal format;
		            //Convert it to hexadecimal format
		            StringBuilder sb = new StringBuilder();
		            for(int i=0; i< bytes.length ;i++) {
		                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		            }
		            
		            //Get complete hashed password in hex format
		            generatedPassword = sb.toString();
		        }
		        catch (NoSuchAlgorithmException e) {
		        	log4j.error("Cannot hash the password.");
		            e.printStackTrace();
		        }				
				return generatedPassword;
				
			}

		@Override
		public byte[] getImage(String email) {
			byte[] imgData = null;
			System.out.println(email);
			try {
				Statement statement = conn.createStatement();
				// To store the results from the search
				ResultSet result = statement.executeQuery("Select profilepic, certificate from user where email='" + email + "';");
				
				while(result.next()) {
					imgData = result.getBytes(1);
				}

			}catch(Exception e) {
				log4j.error("Cannot get image.");
			}
			return imgData;
		}
		@Override
		public byte[] getCertificate(String email) {
			byte[] imgData = null;
			System.out.println(email);
			try {
				Statement statement = conn.createStatement();
				// To store the results from the search
				ResultSet result = statement.executeQuery("Select certificate from user where email='" + email + "';");
				
				while(result.next()) {
					imgData = result.getBytes(1);
				}

			}catch(Exception e) {
				log4j.error("Cannot get image.");
			}
			return imgData;
		}

}
