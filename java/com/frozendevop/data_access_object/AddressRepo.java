package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frozendevop.models.Address;

public interface AddressRepo extends JpaRepository<Address, String>
{
	@Query(value="select * from address where uid='?1' and active='yes'",nativeQuery=true)
	public List<Address> findByUser(String uid);

}
