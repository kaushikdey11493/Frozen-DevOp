package com.frozendevop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address 
{
	@Id
	@Column(name="id",length=10)
	private String id;
	
	@Column(name="uid",length=100,nullable=false)
	private String uid;
	
	@Column(name="country",length=100,nullable=false)
	private String country;
	
	@Column(name="state",length=100,nullable=false)
	private String state;
	
	@Column(name="city",length=100,nullable=false)
	private String city;
	
	@Column(name="town",length=100,nullable=false)
	private String town;
	
	@Column(name="street_no",length=100,nullable=false)
	private String street_no;
	
	@Column(name="house_no",length=100,nullable=false)
	private String house_no;
	
	@Column(name="nearby",length=100,nullable=false)
	private String nearby;
	
	@Column(name="active",length=3,nullable=false)
	private String active; 
	
	@Column(name="pincode",length=15,nullable=false)
	private String pincode; 
	
	
	public Address() {
		this.id = "";
		this.uid = "";
		this.country = "";
		this.state = "";
		this.city = "";
		this.town = "";
		this.street_no = "";
		this.house_no = "";
		this.nearby = "";
		this.active = "";
		this.pincode = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreet_no() {
		return street_no;
	}

	public void setStreet_no(String street_no) {
		this.street_no = street_no;
	}

	public String getHouse_no() {
		return house_no;
	}

	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}

	public String getNearby() {
		return nearby;
	}

	public void setNearby(String nearby) {
		this.nearby = nearby;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", uid=" + uid + ", country=" + country + ", state=" + state + ", city=" + city
				+ ", town=" + town + ", street_no=" + street_no + ", house_no=" + house_no + ", nearby=" + nearby
				+ ", active=" + active + ", pincode=" + pincode + "]";
	}
	
	public String getString() {
		return id + "<@@@>" + uid + "<@@@>" + country + "<@@@>" + state + "<@@@>" + city
				+ "<@@@>" + town + "<@@@>" + street_no + "<@@@>" + house_no + "<@@@>" + nearby
				+ "<@@@>" + active + "<@@@>" + pincode;
	}

			
}
