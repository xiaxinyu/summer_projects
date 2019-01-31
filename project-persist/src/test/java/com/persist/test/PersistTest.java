package com.persist.test;

import java.util.List;

import com.persist.bean.User;
import com.persist.core.SqlSession;
import com.persist.mapper.UserMapper;

public class PersistTest {
	public static void main(String[] args) {
		UserMapper mapper = SqlSession.getMapper(UserMapper.class);
		int insertUser = mapper.saveUser(1, "summer", "programer");
		System.out.println("Effect users :" + insertUser);

		List<User> users = mapper.queryUser("programer");
		for (User user : users) {
			System.out.println(user);
		}
	}
}
