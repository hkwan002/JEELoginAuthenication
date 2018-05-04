package com.optimum.pojo;

import java.io.InputStream;
import java.sql.Blob;

import com.optimum.dao.UserDAO;
import com.optimum.dao.UserDAOImpl;

public class User {
//	private String username;
	private String userPassword;
	private String name;
	private String nric;
	private String gender;
	private String dob;
	private String address;
	private String country;
	private String qualification;
	private InputStream certificate;
	private String department;
	private String email;
	private String mobile;
	private String status;
	private boolean firstlogin;
	private String EmployeeID;
	private int noofattempts;
	private InputStream profilepic;
	private Blob image;
	private byte[] imgdata;
	
	private UserDAO refUserDAO;
	public User() {
	refUserDAO = new UserDAOImpl();
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public byte[] getImgdata() {
		return imgdata;
	}
	public void setImgdata(byte[] imgdata) {
		this.imgdata = imgdata;
	}

	public InputStream getProfilepic() {
		return profilepic;
	}
	public void setProfilepic(InputStream profilepic) {
		this.profilepic = profilepic;
	}
	public int getNoofattempts() {
		return noofattempts;
	}
	public void setNoofattempts(int noofattempts) {
		this.noofattempts = noofattempts;
	}
	public String getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}
	public InputStream getCertificate() {
		return certificate;
	}
	public void setCertificate(InputStream inputStream) {
		this.certificate = inputStream;
	}
	

	public boolean isFirstlogin() {
		return firstlogin;
	}
	public void setFirstlogin(boolean firstlogin) {
		this.firstlogin = firstlogin;
	}


	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = refUserDAO.hashPassword(userPassword);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNric() {
		return nric;
	}
	public void setNric(String nric) {
		this.nric = nric;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	
}
