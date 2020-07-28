package com.udemy.ppmtool.exception;

public class ProjectNotFoundExceptionResponse {

	private String message;

	public ProjectNotFoundExceptionResponse(String message) {
		this.message = message;
	}

	public String getProjectIdentifier() {
		return message;
	}

	public void setProjectIdentifier(String message) {
		this.message = message;
	}
}
