package com.anik.model;

public class ServletResponse<T> {
	private T data;
	private String error;

	public T getData() {
		return data;
	}

	public void setData(final T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}
}
