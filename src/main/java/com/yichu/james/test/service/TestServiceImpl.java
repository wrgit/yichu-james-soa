package com.yichu.james.test.service;

public class TestServiceImpl implements TestService {

	public String eat(String param) {
		System.out.println("TestServiceImpl eat"+ param);
		return null;
	}

}
