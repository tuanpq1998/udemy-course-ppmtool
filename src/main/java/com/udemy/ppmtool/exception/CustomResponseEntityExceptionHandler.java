/**
 * @author admin
 * @date 08-02-2020
 */

package com.udemy.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler
	public final ResponseEntity<ProjectIdentifierExceptionResponse> handlerProjectIdentifierException(ProjectIdentifierException ex) {
		return new ResponseEntity<ProjectIdentifierExceptionResponse>(new ProjectIdentifierExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<ProjectNotFoundExceptionResponse> handlerProjectNotFoundException(ProjectNotFoundException ex) {
		return new ResponseEntity<ProjectNotFoundExceptionResponse>(new ProjectNotFoundExceptionResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public final ResponseEntity<UsernameAlreadyExistResponse> handlerUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
		return new ResponseEntity<UsernameAlreadyExistResponse>(new UsernameAlreadyExistResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
}

