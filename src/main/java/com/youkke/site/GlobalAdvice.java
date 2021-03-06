package com.youkke.site;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.youkke.site.utils.ServiceException;


@ControllerAdvice
public class GlobalAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAdvice.class);

	@Autowired
	private MessageSource messageSource;

	@ResponseBody
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> businessExceptionHandler(ServiceException be) {
		System.err.println("-----------------error");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(be.getCode(), be.getField());
		return map;
	}

}
