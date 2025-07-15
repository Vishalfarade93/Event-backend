package com.event.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleException {

	@ExceptionHandler(ResourceNotFoundException.class)
	ResponseEntity<ResponseMsg> resourceNotFoundHandler(RuntimeException e) {

		String message = e.getMessage();
		ResponseMsg success = ResponseMsg.builder().msg(message).status(HttpStatus.NOT_FOUND).success(false).build();
		return new ResponseEntity<ResponseMsg>(success, HttpStatus.NOT_FOUND);
	}

}
