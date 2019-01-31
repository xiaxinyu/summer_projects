package com.persist.mapper;

import java.util.List;

import com.persist.bean.User;
import com.persist.core.annotation.Insert;
import com.persist.core.annotation.Query;

public interface UserMapper {
	@Insert("insert  table User(id, username, position) values (#{id}, #{userName}, #{position});")
	public int saveUser(Integer id, String userName, String position);

	@Query("select * from User where id=#{id}")
	public User findUser(Integer id);

	@Query("select * from User where position = #{position}")
	public List<User> queryUser(String position);
}
