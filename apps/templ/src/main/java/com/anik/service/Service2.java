package com.anik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service2 {
	private static final Logger LOG = LoggerFactory.getLogger(Service2.class);

	public Service2() {
		LOG.info("Service2 constructor");
	}

	public String getData() {
		return "Aaaa";
	}
}
