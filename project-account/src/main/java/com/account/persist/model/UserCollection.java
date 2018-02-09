package com.account.persist.model;

import java.util.List;

import com.account.core.tool.StringTool;

/**
 * Created by Summer.Xia on 9/22/2015.
 */
public class UserCollection {
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public boolean isNotEmpty(){
		if(users != null && users.size() > 0){
			return true;
		}
		return false;
	}
	
	public User getUser(String userName){
		User result = null;
		if(users != null && users.size() > 0 && !StringTool.isNullOrEmpty(userName)){
			for (User user : users) {
				if(userName.equals(user.getUserName())){
					result = user;
					break;
				}
			}
		}
		return result;
	}
}
