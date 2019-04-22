package com.frozendevop.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Publisher
{
	@Id
	@Column(name="email",length=100,nullable=false)
	private String email;
	
	@Column(name="name",length=100,nullable=false)
	private String name;
	
	@Column(name="password",length=500,nullable=false)
	private String password;
	
	@Column(name="phone",length=20,nullable=false)
	private String phone;
	
	@Column(name="address",length=200,nullable=false)
	private String address;
	
	@Column(name="website",length=100)
	private String website;
	
	@Column(name="noofbooks")
	private int noofbooks;
	
	@Column(name="active",length=2)
	private String active;
	
	@Column(name="date")
	private String date;
	
	@Column(name="ekey",nullable=false)
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getNoofbooks() {
		return noofbooks;
	}

	public void setNoofbooks(int noofbooks) {
		this.noofbooks = noofbooks;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getEkey() {
		return ekey;
	}

	public void setEkey(String ekey) {
		this.ekey = ekey;
	}

	@Override
	public String toString() {
		return "Publisher [email=" + email + ", name=" + name + ", password=" + password + ", phone=" + phone
				+ ", address=" + address + ", website=" + website + ", noofbooks=" + noofbooks + ", active=" + active
				+ ", date=" + date + ", ekey=" + ekey + "]";
	}

	public String getString() {
		return email + "<@@@>" + name + "<@@@>" + password + "<@@@>" + phone
				+ "<@@@>" + address + "<@@@>" + website + "<@@@>" + noofbooks + "<@@@>" + active
				+ "<@@@>" + date + "<@@@>" + ekey ;
	}
		
}
