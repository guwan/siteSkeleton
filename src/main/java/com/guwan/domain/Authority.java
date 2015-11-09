package com.guwan.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Authority {
	@Column(nullable = true)
	private String username;
	@Column(nullable = true)
	private String Authority;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthority() {
		return Authority;
	}
	public void setAuthority(String authority) {
		Authority = authority;
	}

}
