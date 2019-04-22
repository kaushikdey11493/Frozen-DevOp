package com.frozendevop.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee 
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
	
	@Column(name="jobid",length=100,nullable=false)
	private String jobid;
	
	@Column(name="noofpro",nullable=true)
	private int noofpro;
	
	@Column(name="date",nullable=true)
	private String date;
	
	@Column(name="active",nullable=true)
	private String active;
	
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
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public int getNoofpro() {
		return noofpro;
	}
	public void setNoofpro(int noofpro) {
		this.noofpro = noofpro;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getEkey() {
		return ekey;
	}
	public void setEkey(String ekey) {
		this.ekey = ekey;
	}
	@Override
	public String toString() {
		return "Employee [email=" + email + ", name=" + name + ", password=" + password + ", phone=" + phone
				+ ", jobid=" + jobid + ", noofpro=" + noofpro + ", date=" + date + ", active=" + active + ", ekey="
				+ ekey + "]";
	}
	
	public String getString() {
		return email + "<@@@>" + name + "<@@@>" + password + "<@@@>" + phone
				+ "<@@@>" + jobid + "<@@@>" + noofpro + "<@@@>" + date + "<@@@>" + active + "<@@@>"
				+ ekey;
	}
		
}
