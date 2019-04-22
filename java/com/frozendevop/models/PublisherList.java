package com.frozendevop.models;

import java.util.List;

public class PublisherList 
{
	private List<Publisher> list;

	public List<Publisher> getList() {
		return list;
	}

	public void setList(List<Publisher> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PublisherList [list=" + list + "]";
	}
	
}
