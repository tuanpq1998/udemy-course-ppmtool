package com.udemy.ppmtool.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.ppmtool.entity.ProjectTask;
import com.udemy.ppmtool.service.ProjectTaskService;
import com.udemy.ppmtool.service.ValidationErrorService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private ValidationErrorService errorService;

	@PostMapping("/{projectIdentifier}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String projectIdentifier, Principal principal) {
		if (result.hasErrors())
			return errorService.handleError(result);
		ProjectTask newProjectTask = projectTaskService.addProjectTask(projectTask, projectIdentifier,
				principal.getName());
		return new ResponseEntity<ProjectTask>(newProjectTask, HttpStatus.CREATED);
	}

	@GetMapping("/{projectIdentifier}")
	public List<ProjectTask> getProjectBacklog(@PathVariable String projectIdentifier, Principal principal) {
		return projectTaskService.findBacklogById(projectIdentifier, principal.getName());
	}

	@GetMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<?> getProjectTask(@PathVariable String projectIdentifier,
			@PathVariable String projectSequence, Principal principal) {
		return new ResponseEntity<ProjectTask>(
				projectTaskService.findByProjectSequence(projectIdentifier, projectSequence, principal.getName()), HttpStatus.OK);
	}

	@PatchMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String projectIdentifier, @PathVariable String projectSequence, Principal principal) {

		if (result.hasErrors())
			return errorService.handleError(result);

		ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, projectIdentifier,
				projectSequence, principal.getName());

		return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);

	}

	@DeleteMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String projectIdentifier,
			@PathVariable String projectSequence, Principal principal) {

		projectTaskService.deleteByProjectSequence(projectIdentifier, projectSequence, principal.getName());

		return new ResponseEntity<String>("Project task '" + projectSequence + "' was deleted!", HttpStatus.OK);

	}
}
