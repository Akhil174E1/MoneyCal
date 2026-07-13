package com.akh.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akh.dao.MoneyDao;
import com.akh.util.MoneyVo;
import com.akh.util.User;

@Service
public class MoneyService {
	private static final Logger logger =
            LoggerFactory.getLogger(MoneyService.class);
	
	@Autowired
	public MoneyDao moneyDao;
	
	
	public List<MoneyVo> getAllDetails(User user){
		List<MoneyVo> m = moneyDao.findByUser(user);
		return m;
	}
	
	public String insertDetails(MoneyVo moneyVo) {
		moneyDao.save(moneyVo);
		logger.info("Money Service ::::::inside insertDetails::::"+moneyVo.toString());
		return moneyVo.getId()+" : inserted successfully..";
	}
	
	public Float totalAmount(User user) {
		Float total = moneyDao.getTotalAmountByUserId(user.getId());
		return total != null ? total : 0f;
	}
	
	public String updateDetails(MoneyVo moneyVo) {
		Integer id = moneyVo.getId();
		Optional<MoneyVo> m = moneyDao.findById(id);
		if(m.isPresent()) {
			moneyDao.save(moneyVo);
			logger.info("Money Service ::::::inside updateDetails : id: "+id);
			return moneyVo.getId()+" : Updated successfully..";
		}
		return moneyVo.getId()+" : Not found ";
		
	}
	
	public String deleteDetails(Integer id) {
		logger.info("Money Service ::::::inside deleteDetails : id: "+id);
		Optional<MoneyVo> m = moneyDao.findById(id);
		if(m.isPresent()) {
			moneyDao.deleteById(id);
			return id+" : Deleted successfully..";
		}
		return id+" : Not found ";
		
	}
	

}
