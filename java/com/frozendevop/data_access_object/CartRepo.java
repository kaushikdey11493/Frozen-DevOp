package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frozendevop.models.Cart;
import com.frozendevop.models.Order;

public interface CartRepo extends JpaRepository<Cart, String>{

	public List<Cart> findByUid(String uid);
}
