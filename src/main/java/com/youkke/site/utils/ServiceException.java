package com.youkke.site.utils;

import java.util.Arrays;
import java.util.Objects;

public class ServiceException extends RuntimeException {
	private final String field;
	private final String code;
	private final Object[] params;

	public ServiceException(String field, String code) {
		super("Service Message Code: " + code);
		this.field = field;
		this.code = code;
		this.params = new Object[0];
	}

	public String getCode() {
		return code;
	}

	public Object[] getParams() {
		return params;
	}

	public String getField() {
		return field;
	}
}
