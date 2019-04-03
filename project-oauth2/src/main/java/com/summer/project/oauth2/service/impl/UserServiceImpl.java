package com.summer.project.oauth2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.summer.project.oauth2.bean.CustomUserDetails;
import com.summer.project.oauth2.bean.User;
import com.summer.project.oauth2.mapper.UsersMapper;
import com.summer.project.oauth2.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UsersMapper usersMapper;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = usersMapper.getUser(s);
		return new CustomUserDetails(user);
	}
}
