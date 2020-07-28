package com.udemy.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.ppmtool.entity.User;
import com.udemy.ppmtool.payload.JsonWebTokenLoginSuccessResponse;
import com.udemy.ppmtool.payload.LoginRequest;
import com.udemy.ppmtool.security.JwtProvider;
import com.udemy.ppmtool.service.UserService;
import com.udemy.ppmtool.service.ValidationErrorService;
import com.udemy.ppmtool.validator.UserValidator;
import static com.udemy.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private ValidationErrorService errorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<?> regiterUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		if (user.getPassword() == null) user.setPassword("");
		if (user.getConfirmPassword() == null) user.setConfirmPassword("");
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) 
			return errorService.handleError(bindingResult);
		User newUser = userService.save(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		if (result.hasErrors()) 
			return errorService.handleError(result);
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + jwtProvider.generateToke(authentication);
		
		return ResponseEntity.ok(new JsonWebTokenLoginSuccessResponse(true, jwt));
	}
}
