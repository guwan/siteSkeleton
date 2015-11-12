package com.guwan.support;

public class TestTools {

	public static void main(String[] args) {
		BCryptEncoder bce = new BCryptEncoder();
		System.out.println(bce.encode("password"));
	}

}
