package com.anik.service;

import javax.inject.Inject;

public class Service {
	@Inject
	private Service2 service2;

	public String getData() {
		return service2.getData();
	}

}
