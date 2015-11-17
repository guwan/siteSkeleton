package com.guwan.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import com.guwan.support.ApplicationConfig;

@Entity
public class Authority implements GrantedAuthority{
    /**
	 * 
	 */
	private static final long serialVersionUID = ApplicationConfig.serialVersionUID;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="username",nullable = true)
	private User username;
	@Column(nullable = true)
	private String authority;
	
	public Authority(User user, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}
	public Authority(String username, String authority) {
		super();
		User user=new User();
		user.setUsername(username);
		this.username = user;
		this.authority = authority;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public User getUsername() {
		return username;
	}
	public void setUsername(User username) {
		this.username = username;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authority other = (Authority) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
