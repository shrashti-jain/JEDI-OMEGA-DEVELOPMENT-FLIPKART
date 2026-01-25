/**
 * 
 */
package com.flipfit.bean;

/**
 * 
 */
public class User {

	/**
	 * @param args
	 */
	private String userId;
	private String name;
	private String email;
	private String phone;
	
	public User(){}

	public User(String userId, String name, String email, String phone){
		this.userId=userId;
		this.name=name;
		this.email=email;
		this.phone=phone;
	}
	
	public void register(){}
	public void login(){}
	public void logout(){}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
