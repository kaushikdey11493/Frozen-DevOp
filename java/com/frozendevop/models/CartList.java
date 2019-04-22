package com.frozendevop.models;

import java.util.List;

public class CartList {
	
	private List<Cart> cartList;

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	@Override
	public String toString() {
		return "CartList [cartList=" + cartList + "]";
	}
	
}
