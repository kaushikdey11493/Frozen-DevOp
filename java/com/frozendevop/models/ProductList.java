package com.frozendevop.models;

import java.util.List;

public class ProductList
{
	private List<Product> list;

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ProductList [list=" + list + "]";
	}
	
}
