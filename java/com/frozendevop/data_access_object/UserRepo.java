package com.frozendevop.data_access_object;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frozendevop.models.User;

public interface UserRepo extends JpaRepository<User, String> {

}
