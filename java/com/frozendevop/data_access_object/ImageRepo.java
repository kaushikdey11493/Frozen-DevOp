package com.frozendevop.data_access_object;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frozendevop.models.Image;

public interface ImageRepo extends JpaRepository<Image, String>
{
	public List<Image> findByUpid(String upid);
	
	public void deleteByUpid(String upid);
}
