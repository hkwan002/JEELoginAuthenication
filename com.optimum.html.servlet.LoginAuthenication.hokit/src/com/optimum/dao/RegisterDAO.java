package com.optimum.dao;

import java.io.InputStream;

import com.optimum.pojo.User;

public interface RegisterDAO {
	
	boolean newUser(User refUser, InputStream inputStream, String tempPassword);
}
