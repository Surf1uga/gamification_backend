package com.bootcamp.soleraproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class TeamNotFoundException extends RuntimeException {

	public TeamNotFoundException(String message) {
		super(message);
	}
	
}
