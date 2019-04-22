package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frozendevop.models.Employee;
import com.frozendevop.models.Product;

public interface ProductRepo extends JpaRepository<Product,String>
{
	@Query(value="select * from product where active=?1 order by date desc",nativeQuery=true)
	public List<Product> findByActive(String active);
	
	@Query(value="select * from product where ( pid like %?1% or pname like %?1% or author like %?1% or isbn like %?1% or category in (select catid from category where catid like %?1% or catname like %?1%) ) and active=?2",nativeQuery=true)
	public List<Product> search(String value,String active);
	
	public List<Product> findByCategoryAndActive(String category,String active);
}
