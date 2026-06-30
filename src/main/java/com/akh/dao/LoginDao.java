package com.akh.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akh.util.User;



@Repository
public interface LoginDao extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}
