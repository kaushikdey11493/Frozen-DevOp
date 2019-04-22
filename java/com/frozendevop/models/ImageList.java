package com.frozendevop.models;

import java.util.List;

public class ImageList
{
	private List<Image> list;

	public List<Image> getList() {
		return list;
	}

	public void setList(List<Image> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ImageList [list=" + list + "]";
	}
	
}
