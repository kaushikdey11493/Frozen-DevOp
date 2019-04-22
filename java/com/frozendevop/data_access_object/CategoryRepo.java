package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frozendevop.models.Category;

public interface CategoryRepo extends JpaRepository<Category, String>{

	@Query(value="select * from category where catname like %?1%",nativeQuery=true)
	public List<Category> search(String value);
	
	public List<Category> findByParent(String parent);
}
