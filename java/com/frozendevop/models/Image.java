package com.frozendevop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Image
{
	@Id
	@Column(name="imgsrc",length=300,nullable=false)
	private String imgsrc;
	
	@Column(name="id",length=100,nullable=false)
	private String upid;

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getUpid() {
		return upid;
	}

	public void setUpid(String upid) {
		this.upid = upid;
	}

	@Override
	public String toString() {
		return "Image [imgsrc=" + imgsrc + ", id=" + upid + "]";
	}
	
	public String getString() {
		return imgsrc + "<@@@>" + upid;
	}
}
