package com.summer.project.oauth2.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.summer.project.oauth2.bean.User;

@Mapper
public interface UsersMapper {
	User getUser(String userName);
}
