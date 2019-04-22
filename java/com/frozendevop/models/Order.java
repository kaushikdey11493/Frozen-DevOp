package com.frozendevop.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ordert")
public class Order {
	
	@Id
	private String oid;

	private String uid;
	
	private String pid;
	
	private String date;
	
	public Order(){
		Date d=new Date();
		date=d.toString().substring(4, 7)+"-"+d.toString().substring(8, 10)+"-"+d.toString().substring(24);
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", uid=" + uid + ", pid=" + pid + ", date=" + date + "]";
	}

	public String getString() {
		return oid + "<@@@>" + uid + "<@@@>" + pid + "<@@@>" + date;
	}
	
}
