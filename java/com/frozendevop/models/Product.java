package com.frozendevop.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product
{
	@Id
	@Column(name="pid",length=300,nullable=false)
	private String pid;
	
	@Column(name="pname",length=300,nullable=false)
	private String pname;
	
	@Column(name="pubid",length=100,nullable=false)
	private String pubid;
	
	@Column(name="pdetails",length=3000,nullable=false)
	private String pdetails;
	
	@Column(name="mprice",nullable=false,precision=8,scale=2)
	private double mprice;
	
	@Column(name="sprice",nullable=false,precision=8,scale=2)
	private double sprice;
	
	@Column(name="discount",nullable=false,precision=8,scale=2)
	private double discount;
	
	@Column(name="cprice",nullable=false,precision=8,scale=2)
	private double cprice;
	
	@Column(name="category",length=300,nullable=false)
	private String category;
	
	@Column(name="empid",length=100,nullable=false)
	private String empid;
	
	@Column(name="active",length=3,nullable=false)
	private String active;
	
	@Column
	private String length;
	
	@Column(name="author")
	private String author;
	
	@Column
	private String isbn;
	
	@Column
	private String date;
	
	public Product() {
		this.pid = "";
		this.pname = "";
		this.pubid = "";
		this.pdetails = "";
		this.mprice = 0;
		this.sprice = 0;
		this.discount = 0;
		this.cprice = 0;
		this.category = "";
		this.empid = "";
		this.active = "no";
		this.length = "";
		this.author = "";
		this.isbn="";
		this.date=getNewDate();//(new Date()).toString();
	}
	private String getNewDate() 
	{
		String str[]=new Date().toString().split(" ");
		String s=str[5]+" "+getMonth(str[1])+" "+str[2]+" "+str[3]+" "+str[4]+" "+str[0];
		return s;
	}
	private String getMonth(String string) {
		String month[]= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		int i=0;
		for(i=0;i<12;i++)
		{
			if(month[i].equals(string))
				break;
		}
		if(i<10)
			return "0"+i;
		return ""+i;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPubid() {
		return pubid;
	}
	public void setPubid(String pubid) {
		this.pubid = pubid;
	}
	public String getPdetails() {
		return pdetails;
	}
	public void setPdetails(String pdetails) {
		this.pdetails = pdetails;
	}
	public double getMprice() {
		return mprice;
	}
	public void setMprice(double mprice) {
		this.mprice = mprice;
	}
	public double getSprice() {
		return sprice;
	}
	public void setSprice(double sprice) {
		this.sprice = sprice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getCprice() {
		return cprice;
	}
	public void setCprice(double cprice) {
		this.cprice = cprice;
	}
	public String getEmpid() {
		return empid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
		
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", pubid=" + pubid + ", pdetails=" + pdetails + ", mprice="
				+ mprice + ", sprice=" + sprice + ", discount=" + discount + ", cprice=" + cprice + ", category="
				+ category + ", empid=" + empid + ", active=" + active + ", length=" + length + ", author=" + author
				+ ", isbn=" + isbn + ", date=" + date + "]";
	}

	public String getString() {
		return pid + "<@@@>" + pname + "<@@@>" + pubid + "<@@@>" + pdetails + "<@@@>"
				+ mprice + "<@@@>" + sprice + "<@@@>" + discount + "<@@@>" + cprice + "<@@@>"
				+ category + "<@@@>" + empid + "<@@@>" + active + "<@@@>" + length + "<@@@>" + author
				+ "<@@@>" + isbn + "<@@@>" + date ;
	}

		
}
