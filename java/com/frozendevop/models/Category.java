package com.frozendevop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category
{
	@Id
	@Column(name="catid")
	private String id;
	
	@Column(name="catname")
	private String name;
	
	@Column(name="parent")
	private String parent;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	@Override
	public String toString() {
		return "Category [catid=" + id + ", catname=" + name + ", parent=" + parent + "]";
	}
	
	public String getString() {
		return id + "<@@@>" + name + "<@@@>" + parent;
	}
}
