/**
 * @author admin
 * @date 07-02-2020
 */

package com.udemy.ppmtool.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.ppmtool.entity.Project;
import com.udemy.ppmtool.service.ProjectService;
import com.udemy.ppmtool.service.ValidationErrorService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ValidationErrorService errorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNew(@Valid @RequestBody Project project,
			BindingResult result, Principal principal) {
		if (result.hasErrors()) 
			return errorService.handleError(result);
		Project newProject = projectService.saveOrUpdate(project, principal.getName());
		return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getByIdentifier(@PathVariable String projectId, Principal principal) {
		Project p = projectService.findByIdentifier(projectId, principal.getName());
		return new ResponseEntity<Project>(p, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAll(Principal principal) {
		return projectService.findAllByUsername(principal.getName());
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<String> deleteByIdentifier(@PathVariable String projectId, Principal principal) {
		projectService.deleteByIdentifier(projectId, principal.getName());
		return new ResponseEntity<String>("Delete done!", HttpStatus.OK);
	}
}
