package com.guwan.support;

import java.util.ArrayList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.guwan.domain.Authority;

public class Tools {

	public static void main(String[] args) {
		BCryptEncoder bce = new BCryptEncoder();
		System.out.println(bce.encode("password"));
		
		ArrayList<SimpleGrantedAuthority> sga =new ArrayList<SimpleGrantedAuthority>();		
		ArrayList<Authority> a =new ArrayList<Authority>();
		sga.add(new SimpleGrantedAuthority("ADMIN"));
		a.add(new Authority("admin", "ADMIN"));
	}

}
