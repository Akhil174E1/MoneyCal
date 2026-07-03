package com.akh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.akh.controller.MoneyController;
import com.akh.dao.LoginDao;
import com.akh.util.User;

@Service
public class AuthService {
	
	private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
    private LoginDao loginDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signup(String email, String password) {
    	logger.info("AuthService::::: Inside signup method:::::");
        if (loginDao.existsByEmail(email)) {
            return "Email already registered";
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        loginDao.save(user);
        return "Signup successful";
    }

    public String login(String email, String password) {
        User user = loginDao.findByEmail(email)
                .orElse(null);
        if (user == null) {
        	logger.info("AuthService::::: Inside login method:::::"+email);
            return "User not found";
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password";
        }
        return "Login successful";
    }

}
