package com.frozendevop.models;

import java.util.List;

public class EmployeeList
{
	private List<Employee> list;

	public List<Employee> getList() {
		return list;
	}

	public void setList(List<Employee> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "EmployeeList [list=" + list + "]";
	}
	
}
