package com.udemy.ppmtool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.ppmtool.entity.Backlog;
import com.udemy.ppmtool.entity.Project;
import com.udemy.ppmtool.entity.ProjectTask;
import com.udemy.ppmtool.exception.ProjectNotFoundException;
import com.udemy.ppmtool.repository.ProjectRepository;
import com.udemy.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(ProjectTask projectTask, String projectIdentifier, String username) {
		
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);	
		if (project == null)
			throw new ProjectNotFoundException("Project identifier doesn't exist !");
		
		Backlog backlog = projectService.findByIdentifier(projectIdentifier, username).getBacklog();
		
		projectTask.setBacklog(backlog);
		
		Integer backlogSequence = backlog.getPTSequence();
		backlog.setPTSequence(++backlogSequence);
		projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		if (projectTask.getPriority() == null || projectTask.getPriority() == 0)
			projectTask.setPriority(3);
		
		if (projectTask.getStatus() == null || projectTask.getStatus() == "")
			projectTask.setStatus("TO_DO");
		
		return projectTaskRepository.save(projectTask);
	}

	public List<ProjectTask> findBacklogById(String projectIdentifier, String username) {
		projectService.findByIdentifier(projectIdentifier, username);
			
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
	}

	public ProjectTask findByProjectSequence(String projectIdentifier, String projectSequence, String username) {
		projectService.findByIdentifier(projectIdentifier, username);
		
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);
		if (projectTask == null)
			throw new ProjectNotFoundException("Project task does't exist !");
		
		if (!projectTask.getProjectIdentifier().equals(projectIdentifier))
			throw new ProjectNotFoundException("Project task does't exist in this project !");
		
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String projectSequence, String username) {
		ProjectTask projectTask = findByProjectSequence(projectIdentifier, projectSequence, username);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
	}

	public void deleteByProjectSequence(String projectIdentifier, String projectSequence, String username) {
		ProjectTask projectTask = findByProjectSequence(projectIdentifier, projectSequence, username);
		
		projectTaskRepository.delete(projectTask);
	}
}
