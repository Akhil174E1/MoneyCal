package com.akh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akh.util.MoneyVo;
import com.akh.util.User;


@Repository
public interface MoneyDao extends JpaRepository<MoneyVo, Integer> {
	
	
	@Query("SELECT SUM(m.amount) FROM MoneyVo m WHERE m.user.id = :userId")
	Float getTotalAmountByUserId(@Param("userId") Integer userId);
	
	List<MoneyVo> findByUser(User user);

}
