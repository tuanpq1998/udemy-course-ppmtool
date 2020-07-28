/**
 * @author admin
 * @date 08-02-2020
 */

package com.udemy.ppmtool.exception;

public class ProjectIdentifierExceptionResponse {

	private String projectIdentifier;

	public ProjectIdentifierExceptionResponse(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

}
