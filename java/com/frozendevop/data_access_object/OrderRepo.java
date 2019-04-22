package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frozendevop.models.Order;

public interface OrderRepo extends JpaRepository<Order, String> {

	public List<Order> findByUid(String uid);
}
