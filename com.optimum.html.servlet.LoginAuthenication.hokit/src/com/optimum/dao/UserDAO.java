package com.optimum.dao;

import java.io.IOException;
import java.io.InputStream;

import com.optimum.pojo.User;

public interface UserDAO {

	boolean loginAuthentication(User refUser) throws IOException;
	boolean adminCheck(User refUser);
	boolean firstLogin(User refUser);
	boolean checkStatus(User refUser);
	boolean checkUser(User refUser);
	boolean changeStatus(User refUser);
	boolean checkEmail(User refUser);
	boolean noofattempts(User refUser);
	boolean updateUser(User refUser, InputStream inputStream, InputStream inputStream2);
	void resetattempts(User refUser);
	public String hashPassword(String Password);
	public byte[] getImage(String email);
//	ArrayList<User> getUserList();
	public byte[] getCertificate(String email);
	
}
