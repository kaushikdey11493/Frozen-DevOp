package com.frozendevop.models;

import java.util.List;

public class AddressList
{
	private List<Address> list;

	public List<Address> getList() {
		return list;
	}

	public void setList(List<Address> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "AddressList [list=" + list + "]";
	}
	
}
