package com.guwan.support;

import java.util.ArrayList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.guwan.domain.Authority;
import com.guwan.domain.User;

public class Tools {

	public static void main(String[] args) {
		BCryptEncoder bce = new BCryptEncoder();
		System.out.println(bce.encode("password"));
		
	}

}
