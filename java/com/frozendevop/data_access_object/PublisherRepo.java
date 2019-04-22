package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frozendevop.models.Employee;
import com.frozendevop.models.Publisher;

public interface PublisherRepo extends JpaRepository<Publisher, String>
{
	@Query(value="select * from publisher where email like %?1% or name like %?1%",nativeQuery=true)
	public List<Publisher> search(String value);
}
