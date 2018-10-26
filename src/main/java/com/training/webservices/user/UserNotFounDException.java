package com.training.webservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFounDException extends RuntimeException {

	public UserNotFounDException(String message) {
		super(message);
	}

}
