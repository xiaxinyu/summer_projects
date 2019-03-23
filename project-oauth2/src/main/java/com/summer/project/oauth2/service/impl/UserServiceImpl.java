package com.summer.project.oauth2.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.summer.project.oauth2.bean.CustomUserDetails;
import com.summer.project.oauth2.bean.User;
import com.summer.project.oauth2.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		/* 模拟数据库操作 */
		User user = new User();
		user.setUsername("10086");
		user.setPassword(passwordEncoder.encode("123456"));
		return new CustomUserDetails(user);
	}
}
