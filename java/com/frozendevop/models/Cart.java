package com.frozendevop.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cart 
{
	@Id
	private String cid;
	
	private String uid;
	
	private String pid;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	@Override
	public String toString() {
		return "Cart [cid=" + cid + ", uid=" + uid + ", pid=" + pid + "]";
	}
	
	public String getString() {
		return cid + "<@@@>" + uid + "<@@@>" + pid ;
	}
	
}
