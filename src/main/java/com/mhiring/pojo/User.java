package com.mhiring.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userID;
	
	@Column(name = "first_name")
	@NotBlank
	@Size(min = 3, message = "Name is too short, minimum 2 characters")
	@Size(max = 200, message = "Name is too large, maximum 200 characters")
	private String fname;
	
	@Column(name = "last_name")
	@Size(min = 3, message = "Name is too short, minimum 2 characters")
	@Size(max = 200, message = "Name is too large, maximum 200 characters")
	@NotBlank
	private String lname;
	
	@Column(name = "user_type")
	@NotBlank
	private String userType;
	
	@NotBlank
	@Email(message = "Please enter a valid e-mail address")
	@Column(name = "email")
	private String email; 
	
	@Column(name = "password")
	@NotBlank
	@Size(min = 4, message = "Name is too short, minimum 4 characters")
	@Size(max = 100, message = "Name is too large, maximum 100 characters")
	private String password;

	public User(String fname, String lname, String userType, String email, String password) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.userType = userType;
		this.email = email;
		this.password = password;
	}
	
	public User() {
		
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
