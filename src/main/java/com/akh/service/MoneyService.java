package com.akh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akh.dao.MoneyDao;
import com.akh.util.MoneyVo;

@Service
public class MoneyService {
	
	@Autowired
	public MoneyDao moneyDao;
	
	
	public List<MoneyVo> getAllDetails(){
		List<MoneyVo> m = moneyDao.findAll();
		return m;
	}
	
	public String insertDetails(MoneyVo moneyVo) {
		moneyDao.save(moneyVo);
		return moneyVo.getId()+" : inserted successfully..";
	}
	
	public Float totalAmount() {
		Float total = moneyDao.getTotalAmount();
		return total != null ? total : 0f;
	}
	
	public String updateDetails(MoneyVo moneyVo) {
		Integer id = moneyVo.getId();
		Optional<MoneyVo> m = moneyDao.findById(id);
		if(m.isPresent()) {
			moneyDao.save(moneyVo);
			return moneyVo.getId()+" : Updated successfully..";
		}
		return moneyVo.getId()+" : Not found ";
		
	}
	
	public String deleteDetails(Integer id) {
		Optional<MoneyVo> m = moneyDao.findById(id);
		if(m.isPresent()) {
			moneyDao.deleteById(id);
			return id+" : Deleted successfully..";
		}
		return id+" : Not found ";
		
	}

}
