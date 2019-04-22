package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frozendevop.models.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, String>
{
	@Query(value="select * from employee where email like %?1% or name like %?1%",nativeQuery=true)
	public List<Employee> search(String value);
}
