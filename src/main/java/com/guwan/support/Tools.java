package com.guwan.support;

public class Tools {

	public static void main(String[] args) {
		BCryptEncoder bce = new BCryptEncoder();
		System.out.println(bce.encode("password"));
	}

}
