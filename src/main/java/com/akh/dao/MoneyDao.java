package com.akh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.akh.util.MoneyVo;


@Repository
public interface MoneyDao extends JpaRepository<MoneyVo, Integer> {
	
	
	@Query("SELECT SUM(m.amount) FROM MoneyVo m")
	Float getTotalAmount();

}
