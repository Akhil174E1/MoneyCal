package com.akh.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akh.service.AuthService;
import com.akh.service.MoneyService;
import com.akh.util.MoneyVo;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/money-api")
public class MoneyController {
	
	@Autowired
	public MoneyService moneyService;
	
	
	@Autowired
    private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
	    String result = authService.login(body.get("email"), body.get("password"));
	    
	    if (result.equals("Login successful")) {
	        return ResponseEntity.ok(result); 
	    } else {
	        return ResponseEntity.status(401).body(result); 
	    }
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody Map<String, String> body) {
	    String result = authService.signup(body.get("email"), body.get("password"));
	    
	    if (result.equals("Signup successful")) {
	        return ResponseEntity.ok(result);           
	    } else {
	        return ResponseEntity.status(400).body(result); 
	    }
	}
	
	@PostMapping("/postApi")
	public String insertMoneyDetails(@RequestBody MoneyVo moneyVo) {
		String res = moneyService.insertDetails(moneyVo);
		return res;
	}
	@GetMapping("/getTotal")
	public Float getTotalAmount(){
		Float m = moneyService.totalAmount();
		return m;
	}
	
	@PutMapping("/putApi")
	public String updateMoneyDetails(@RequestBody MoneyVo moneyVo) {
		String res = moneyService.updateDetails(moneyVo);
		return res;
	}
	
	@DeleteMapping("/deleteApi")
	public String deleteMoneyDetails(@PathParam(value = "Id") Integer Id) {
		String res = moneyService.deleteDetails(Id);
		return res;
	}
	
	

}
