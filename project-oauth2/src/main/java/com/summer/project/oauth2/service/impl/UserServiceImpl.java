package com.summer.project.oauth2.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import com.summer.project.oauth2.bean.CustomUserDetails;
import com.summer.project.oauth2.bean.User;
import com.summer.project.oauth2.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		/* 模拟数据库操作 */
		User user = new User();
		user.setUsername("10086");
		user.setPassword("$2a$10$HpJtUVGiON9yCITU5jOCeO73HfTqGKhk21ngILvHLC8aPHozfUUzW");
		return new CustomUserDetails(user);
	}
}
