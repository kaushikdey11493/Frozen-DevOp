package com.frozendevop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User
{
	@Id
	@Column(name="email",length=100,nullable=false)
	private String email;
	
	@Column(name="name",length=100,nullable=false)
	private String name;
	
	@Column(name="phone",length=20,nullable=false)
	private String phone;
	
	@Column(name="password",length=500,nullable=false)
	private String password;
	
	@Column(name="ekey",length=30,nullable=false)
	private String ekey;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEkey() {
		return ekey;
	}
	public void setEkey(String ekey) {
		this.ekey = ekey;
	}
	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", phone=" + phone + ", password=" + password + ", ekey="
				+ ekey + "]";
	}
	
	public String getString() {
		return email + "<@@@>" + name + "<@@@>" + phone + "<@@@>" + password + "<@@@>"
				+ ekey ;
	}
		
}
