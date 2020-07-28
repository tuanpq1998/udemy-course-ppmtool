/**
 * @author admin
 * @date 07-02-2020
 */

package com.udemy.ppmtool.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ValidationErrorService {

	public ResponseEntity<?> handleError(BindingResult result) {
		Map<String, String> errorMap = new HashMap<>();
		for (FieldError e : result.getFieldErrors())
			errorMap.put(e.getField(), e.getDefaultMessage());
		return new ResponseEntity<Map<String, String>>(errorMap,
				HttpStatus.BAD_REQUEST);

	}
}
