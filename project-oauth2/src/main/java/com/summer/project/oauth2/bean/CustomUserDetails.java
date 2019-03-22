package com.summer.project.oauth2.bean;

import java.util.Collections;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 1L;
	private User user;

    public CustomUserDetails(User user) {
     super(user.getUsername(), user.getPassword(), true, true, true, true, Collections.emptySet());
     this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}